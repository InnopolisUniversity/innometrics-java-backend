package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.entity.*;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import com.innopolis.innometrics.agentsgateway.repository.ReposxprojectRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class AgentsHandler {
    private static Logger LOG = LogManager.getLogger();

    @Autowired
    AgentconfigRepository agentconfigRepository;

    @Autowired
    AgentconfigmethodsRepository agentconfigmethodsRepository;

    @Autowired
    ReposxprojectRepository reposxprojectRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestTemplate restTemplate;

    public ProjectListResponse getProjectList(ProjectListRequest request) throws NoSuchFieldException, IllegalAccessException {

        ProjectListResponse ProjectList = new ProjectListResponse();
        Agentconfigmethods agentConfig = agentconfigmethodsRepository.findByAgentidAndOperation(request.getAgentId(), request.getOperation());

        String uri = agentConfig.getEndpoint();//"http://innometric.guru:9098/keytoken";//

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        for (ParamsConfigDTO d : request.getParams()) {
            builder.queryParam(
                    d.getParamname(),
                    d.getValue()
            );
        }

        HttpMethod requestMethod = getRequestType(agentConfig.getRequesttype().toUpperCase());


        ResponseEntity<LinkedHashMap[]> response = restTemplate.exchange(builder.toUriString(), requestMethod/*HttpMethod.POST*/, null, LinkedHashMap[].class);

        HttpStatus status = response.getStatusCode();
        Class<?> responseMapping = ProjectDTO.class;
        ProjectList.setAgentId(request.getAgentId());
        for (LinkedHashMap element : response.getBody()) {
            ProjectDTO projectTmp = new ProjectDTO();
            for (Agentresponseconfig responseF : agentConfig.getResponseParams()) {
                Field f = responseMapping.getDeclaredField(responseF.getParamname());
                f.setAccessible(true);
                f.set(projectTmp, element.get(responseF.getResponseparam()).toString());
            }

            ProjectList.getProjectList().add(projectTmp);
        }


        return ProjectList;
    }


    public Boolean getConnectProject(ConnectProjectRequest request) throws NoSuchFieldException, IllegalAccessException {

        Agentconfigmethods agentConfig = agentconfigmethodsRepository.findByAgentidAndOperation(request.getAgentId(), request.getOperation());

        String uri = agentConfig.getEndpoint();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        for (ParamsConfigDTO d : request.getParams()) {
            builder.queryParam(
                    d.getParamname(),
                    d.getValue()
            );
        }

        HttpMethod requestMethod = getRequestType(agentConfig.getRequesttype().toUpperCase());

        ResponseEntity<LinkedHashMap[]> response = restTemplate.exchange(builder.toUriString(), requestMethod/*HttpMethod.POST*/, null, LinkedHashMap[].class);

        HttpStatus status = response.getStatusCode();

        if (status == HttpStatus.CREATED || status == HttpStatus.OK) {
            Agentconfig agent = agentconfigRepository.getOne(request.getAgentId());
            String repoID = "";
            Reposxproject newrepo = new Reposxproject();
            newrepo.setAgentConfig(agent);
            newrepo.setAgentid(request.getAgentId());
            newrepo.setProjectid(request.getProjectID());
            for (ParamsConfigDTO p : request.getParams()) {
                if (p.getParamname().equalsIgnoreCase(agent.getRepoidfield()))
                    repoID = p.getValue();
            }
            newrepo.setRepoid(repoID);

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
                    for (Object[] row: result) {
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
