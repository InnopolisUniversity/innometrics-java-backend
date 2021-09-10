package com.innopolis.innometrics.agentsgateway.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.innopolis.innometrics.agentsgateway.DTO.ConnectProjectRequest;
import com.innopolis.innometrics.agentsgateway.DTO.MetricsResponse;
import com.innopolis.innometrics.agentsgateway.DTO.ProjectDTO;
import com.innopolis.innometrics.agentsgateway.DTO.ProjectListResponse;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.entity.Agentsxproject;
import com.innopolis.innometrics.agentsgateway.entity.sonarqube.*;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import com.innopolis.innometrics.agentsgateway.repository.sonarqube.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.*;

@Service
public class SonarQubeService {
    private static Logger LOG = LogManager.getLogger();


    final private static String MetricsHistory = "MetricsHistory";
    final private static String SonarAgentId = "Sonarqube";

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectAnalysisRepository projectAnalysisRepository;

    @Autowired
    IssuesRepository issuesRepository;

    @Autowired
    CoverageRepository coverageRepository;

    @Autowired
    MaintainabilityRepository maintainabilityRepository;

    @Autowired
    ReliabilityRepository reliabilityRepository;

    @Autowired
    SecurityRepository securityRepository;

    @Autowired
    SizeAndComplexityRepository sizeAndComplexityRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AgentconfigmethodsRepository agentconfigmethodsRepository;

    @Autowired
    AgentconfigRepository agentconfigRepository;

    @Autowired
    private RestTemplate restTemplate;


    public ProjectListResponse getProjectList() {
        List<Project> Listp = projectRepository.findAll();
        ProjectListResponse response =  new ProjectListResponse();
        for (Project p: Listp) {
            ProjectDTO tmp = new ProjectDTO();

            tmp.setProjectId(p.getId().toString());
            tmp.setProjectName(p.getProjectname());
            tmp.setReference(p.getProjectid());
            response.getProjectList().add(tmp);
        }

        return response;

    }


    public MetricsResponse getMetrics() {

        MetricsResponse response = new MetricsResponse();

        response.getMetricList().put("security_hotspots" , "Security");
        response.getMetricList().put("security_rating" , "Security");
        response.getMetricList().put("new_security_hotspots" , "Security");
        response.getMetricList().put("new_security_hotspots_reviewed" , "Security");
        response.getMetricList().put("new_vulnerabilities" , "Security");
        response.getMetricList().put("classes" , "SizeAndComplexity");
        response.getMetricList().put("lines" , "SizeAndComplexity");
        response.getMetricList().put("ncloc" , "SizeAndComplexity");
        response.getMetricList().put("complexity_in_classes" , "SizeAndComplexity");
        response.getMetricList().put("branch_coverage" , "Coverage");
        response.getMetricList().put("coverage" , "Coverage");
        response.getMetricList().put("line_coverage" , "Coverage");
        response.getMetricList().put("bugs" , "Reliability");
        response.getMetricList().put("alert_status" , "Reliability");
        response.getMetricList().put("reliability_rating" , "Reliability");
        response.getMetricList().put("new_bugs" , "Reliability");
        response.getMetricList().put("blocker_violations" , "Issues");
        response.getMetricList().put("violations" , "Issues");
        response.getMetricList().put("major_violations" , "Issues");
        response.getMetricList().put("minor_violations" , "Issues");
        response.getMetricList().put("reopened_issues" , "Issues");
        response.getMetricList().put("new_blocker_violations" , "Issues");
        response.getMetricList().put("new_critical_violations" , "Issues");
        response.getMetricList().put("new_major_violations" , "Issues");
        response.getMetricList().put("new_minor_violations" , "Issues");
        response.getMetricList().put("new_technical_debt" , "Maintainability");
        response.getMetricList().put("code_smells" , "Maintainability");
        response.getMetricList().put("sqale_rating" , "Maintainability");
        response.getMetricList().put("new_code_smells" , "Maintainability");

        return response;

    }

    public ObjectNode getHistroyOfMetric(String type, Long id) throws Exception {

        Metric metric;
        List<? extends Metric> metrics;
        try {
            switch (type){
                case "coverages":
                    metric = coverageRepository.findById(id).orElse(null);
                    metrics = coverageRepository.findAllByAnalysisid(metric.getAnalysisid());
                    break;
                case "issues":
                    metric = issuesRepository.findById(id).orElse(null);
                    metrics = issuesRepository.findAllByAnalysisid(metric.getAnalysisid());
                    break;
                case "maintainabilities":
                    metric = maintainabilityRepository.findById(id).orElse(null);
                    metrics = maintainabilityRepository.findAllByAnalysisid(metric.getAnalysisid());
                    break;
                case "reliabilities":
                    metric = reliabilityRepository.findById(id).orElse(null);
                    metrics = reliabilityRepository.findAllByAnalysisid(metric.getAnalysisid());
                    break;
                case "securities":
                    metric = securityRepository.findById(id).orElse(null);
                    metrics = securityRepository.findAllByAnalysisid(metric.getAnalysisid());
                    break;
                case "sizeandcomplexities":
                    metric = sizeAndComplexityRepository.findById(id).orElse(null);
                    metrics = sizeAndComplexityRepository.findAllByAnalysisid(metric.getAnalysisid());
                    break;
                default:
                    metric = null;
                    metrics = null;

            }
        }catch (NullPointerException e){
            throw new Exception("metric is not found");
        }

        metrics = metrics.stream().filter(m -> m.getId() != metric.getId()).collect(Collectors.toList());

        return objectMapper.createObjectNode()
                .putPOJO("metric",metric)
                .putPOJO("history",metrics);




    }



    public Object getHistroyOfMetrics(String sonarProjectId) throws NoSuchFieldException, IllegalAccessException {
        Agentconfig agent = agentconfigRepository.findByAgentname(SonarAgentId);
        Agentconfigmethods agentConfig = agentconfigmethodsRepository.findByAgentidAndOperation(agent.getAgentid(), MetricsHistory);

        String uri = agentConfig.getEndpoint();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        Class<?> agentConfigMapping = Agentsxproject.class;

        Class<?> requestMapping = ConnectProjectRequest.class;

        builder.queryParam("projectId", sonarProjectId);



        HttpMethod requestMethod = getRequestType(agentConfig.getRequesttype().toUpperCase());

        ResponseEntity<Object> response = restTemplate.exchange(builder.toUriString(), requestMethod, null, Object.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
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
