package com.innopolis.innometrics.agentsgateway.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.innopolis.innometrics.agentsgateway.entity.sonarqube.*;
import com.innopolis.innometrics.agentsgateway.repository.sonarqube.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.*;

@Service
public class SonarQubeService {
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


//    Map<Class<? extends Metric>, JpaRepository<? extends Metric,Long>> metircToRepo = new HashMap<>();
//
//    @PostConstruct
//    public void postLoad() {
//        metircToRepo.put(Issues.class,issuesRepository);
//        metircToRepo.put(Coverage.class,coverageRepository);
//        metircToRepo.put(Maintainability.class,maintainabilityRepository);
//        metircToRepo.put(Reliability.class,reliabilityRepository);
//        metircToRepo.put(Security.class,securityRepository);
//        metircToRepo.put(Sizeandcomplexity.class,sizeAndComplexityRepository);
//    }


    // TODO: 20.11.2020 add filter by timeshtamp
    public Coverage getLastCoverage(Long projectAnalysisid) {
        ProjectAnalysis projectAnalysis = projectAnalysisRepository.findById(projectAnalysisid).orElse(null);
        assertNotNull(projectAnalysis, "no project analysis found");

        List<Coverage> coverages = coverageRepository.findAllByAnalysisid(projectAnalysisid);


        return coverages.get(0);

    }

    public ArrayNode getMetrics(List<Long> projectIds) {
        List<Project> projects;

        if (projectIds != null) {
            projects = projectRepository.findByIdIn(projectIds);
        } else {
            projects = projectRepository.findAll();
        }

        ArrayNode result = objectMapper.createArrayNode();

        for (Project project : projects) {
            Map<String,List<? extends Metric>> metrics = new HashMap<>();

            ProjectAnalysis analysis = projectAnalysisRepository.findByProjectid(project.getId());



            List<Coverage> coverages = coverageRepository.findAllByAnalysisid(analysis.getId());
            metrics.put("coverages", coverages);

            List<Issues> issues = issuesRepository.findAllByAnalysisid(analysis.getId());
            metrics.put("issues", issues);

            List<Maintainability> maintainabilities = maintainabilityRepository.findAllByAnalysisid(analysis.getId());
            metrics.put("maintainabilities", maintainabilities);

            List<Reliability> reliabilities = reliabilityRepository.findAllByAnalysisid(analysis.getId());
            metrics.put("reliabilities", reliabilities);

            List<Security> securities = securityRepository.findAllByAnalysisid(analysis.getId());
            metrics.put("securities", securities);

            List<Sizeandcomplexity> sizeandcomplexities = sizeAndComplexityRepository.findAllByAnalysisid(analysis.getId());
            metrics.put("sizeandcomplexities", sizeandcomplexities);


            result.add(
                    objectMapper.createObjectNode()
                            .put("project_id",project.getId())
                            .putPOJO("metrics",metrics)
            );
        }

        return result;

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

}
