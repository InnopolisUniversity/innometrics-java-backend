package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.entity.*;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentsxprojectRepository;
import com.innopolis.innometrics.agentsgateway.repository.ReposxprojectRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
/*import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.oauth1.*;*/
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

//import org.springframework.social.connect.ApiAdapter;

@Service
public class AgentsHandler {
    private static Logger LOG = LogManager.getLogger();

    final private static String GetReposList = "ProjectList";
    final private static String ConnectRepo = "ProjectConnect";

    @Autowired
    AgentconfigRepository agentconfigRepository;

    @Autowired
    AgentconfigmethodsRepository agentconfigmethodsRepository;

    @Autowired
    ReposxprojectRepository reposxprojectRepository;

    @Autowired
    AgentsxprojectRepository agentsxprojectRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestTemplate restTemplate;

    public ProjectListResponse getProjectList(Integer AgentID, Integer ProjectId) throws NoSuchFieldException, IllegalAccessException {

        ProjectListResponse ProjectList = new ProjectListResponse();
        Agentconfigmethods agentConfig = agentconfigmethodsRepository.findByAgentidAndOperation(AgentID, GetReposList);
        Agentsxproject agentskeys = agentsxprojectRepository.findByAgentidAndProjectid(AgentID, ProjectId);
        String uri = agentConfig.getEndpoint();//"http://innometric.guru:9098/keytoken";//

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        Class<?> requestMapping = Agentsxproject.class;

        for (Agentconfigdetails param : agentConfig.getParams()) {

            String paramValue = "";
            if (param.getRequestparam() != null) {
                LOG.info("getRequestparam -> " + param.getRequestparam());
                Field f = requestMapping.getDeclaredField(param.getRequestparam());
                f.setAccessible(true);
                paramValue = f.get(agentskeys).toString();
            } else {
                paramValue = param.getDefaultvalue();
                LOG.info("getDefaultvalue -> " + param.getDefaultvalue());
            }


            builder.queryParam(
                    param.getParamname(),
                    paramValue
            );
        }

        HttpMethod requestMethod = getRequestType(agentConfig.getRequesttype().toUpperCase());

        ResponseEntity<LinkedHashMap[]> response = restTemplate.exchange(builder.toUriString(), requestMethod/*HttpMethod.POST*/, null, LinkedHashMap[].class);

        HttpStatus status = response.getStatusCode();
        Class<?> responseMapping = ProjectDTO.class;
        ProjectList.setAgentId(AgentID);
        for (LinkedHashMap element : response.getBody()) {
            ProjectDTO projectTmp = new ProjectDTO();
            for (Agentresponseconfig responseF : agentConfig.getResponseParams()) {
                Field f = responseMapping.getDeclaredField(responseF.getParamname());
                f.setAccessible(true);
                f.set(projectTmp, element.get(responseF.getResponseparam()).toString());
            }

            ProjectList.getProjectList().add(projectTmp);
        }

        for (ProjectDTO project :ProjectList.getProjectList()) {
            List<Reposxproject> saved = reposxprojectRepository.findByRepoid(project.getReference());
            if(saved.size() > 0) project.setIsconnected("Y");
            else project.setIsconnected("N");
        }

        return ProjectList;
    }


    public Boolean getConnectProject(ConnectProjectRequest request) throws NoSuchFieldException, IllegalAccessException {
        LOG.info("getConnectProject...");
        Agentconfigmethods agentConfig = agentconfigmethodsRepository.findByAgentidAndOperation(request.getAgentId(), ConnectRepo);
        Agentsxproject agentskeys = agentsxprojectRepository.findByAgentidAndProjectid(request.getAgentId(), request.getProjectID());

        String uri = agentConfig.getEndpoint();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        Class<?> agentConfigMapping = Agentsxproject.class;

        Class<?> requestMapping = ConnectProjectRequest.class;
        LOG.info("setting paramters...");
        for (Agentconfigdetails param : agentConfig.getParams()) {
            String paramValue = "";

            if (("request").equalsIgnoreCase(param.getRequesttype())) {
                Field f = requestMapping.getDeclaredField(param.getRequestparam());
                f.setAccessible(true);
                paramValue = f.get(request).toString();
            } else {
                Field f = agentConfigMapping.getDeclaredField(param.getRequestparam());
                f.setAccessible(true);
                paramValue = f.get(agentskeys).toString();
            }
            LOG.info("setting " + param.getParamname() + " -> " + paramValue);
            builder.queryParam(
                    param.getParamname(),
                    paramValue
            );
        }

        /*
        for (ParamsConfigDTO d : request.getParams()) {
            builder.queryParam(
                    d.getParamname(),
                    d.getValue()
            );
        }
        */

        HttpMethod requestMethod = getRequestType(agentConfig.getRequesttype().toUpperCase());
        LOG.info("creating webhook...");
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(builder.toUriString(), requestMethod, null, LinkedHashMap.class);

        HttpStatus status = response.getStatusCode();
        LOG.info("response -> " + status);
        if (status == HttpStatus.CREATED || status == HttpStatus.OK) {
            Reposxproject newrepo = new Reposxproject();
            newrepo.setAgentid(request.getAgentId());
            newrepo.setProjectid(request.getProjectID());
            newrepo.setRepoid(request.getRepoReference());
            newrepo.setIsactive("Y");
            LOG.info("Saving repo connected");
            reposxprojectRepository.save(newrepo);
            return true;
        }

        return false;
    }

    public RepoDataPerProjectResponse getRepoDataPerProject(Integer ProjectId) {
        List<Reposxproject> repos = reposxprojectRepository.findByProjectid(ProjectId);
        RepoDataPerProjectResponse response = new RepoDataPerProjectResponse();
        for (Reposxproject repo : repos) {
            Agentconfig agent = agentconfigRepository.getOne(repo.getAgentid());
            RepoResponseDTO repoResponse = new RepoResponseDTO();
            repoResponse.setAgentname(agent.getAgentname());
            for (Agentdataconfig datasource : agent.getDataconfig()) {
                String source = datasource.getSchemaname() + "." + datasource.getTablename();
                String fields = datasource.getEventauthorfield() + "  eventauthor, " +
                        datasource.getEventdescriptionfield() + " eventdescription, " +
                        datasource.getEventdatefield() + " eventdate";
                String queryStr = "select " + fields + " from " + source + " where " + agent.getRepoidfield() + " = ?1";

                //LOG.info(queryStr);
                try {
                    Query query = entityManager.createNativeQuery(queryStr);
                    query.setParameter(1, repo.getRepoid());
                    List<Object[]> result = query.getResultList();
                    for (Object[] row : result) {
                        RepoEventDTO eventRow = new RepoEventDTO();
                        eventRow.setEventauthor(row[0].toString());
                        eventRow.setEventdescription(row[1].toString());
                        eventRow.setEventdate(row[2].toString());
                        repoResponse.getEvents().add(eventRow);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            response.getRepos().add(repoResponse);
        }
        return response;
    }

    private HttpMethod getRequestType(String requestType) {
        switch (requestType) {
            case "GET":
                return HttpMethod.GET;
            case "POST":
                return HttpMethod.POST;
            case "PUT":
                return HttpMethod.PUT;
            default:
                return null;
        }


    }

}
