package com.innopolis.innometrics.agentsgateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.innopolis.innometrics.agentsgateway.DTO.MetricsResponse;
import com.innopolis.innometrics.agentsgateway.DTO.ProjectListResponse;
import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Coverage;
import com.innopolis.innometrics.agentsgateway.service.SonarQubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("sonarqube")
public class SonarQubeController {

    @Autowired
    SonarQubeService sonarQubeService;

    @GetMapping("projects")
    public ProjectListResponse getProjectList(){
        return sonarQubeService.getProjectList();
    }

//    @GetMapping("coverage")
//    public Coverage getLastCoverage(@RequestParam Long projectAnalysisid){
//        return sonarQubeService.getLastCoverage(projectAnalysisid);
//    }

    @GetMapping("metrics")
    public MetricsResponse getMetrics(){
        return sonarQubeService.getMetrics();
    }

    @GetMapping("metric/history")
    public ResponseEntity<?> getHistoryForMetric(@RequestParam String InnoProjectId,
                                                 @RequestParam String SonarProjectId) throws Exception {
        Object o = sonarQubeService.getHistroyOfMetrics(SonarProjectId);

        if (o != null)
            return new ResponseEntity<>(o, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
