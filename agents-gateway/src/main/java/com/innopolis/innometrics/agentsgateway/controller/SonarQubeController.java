package com.innopolis.innometrics.agentsgateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Coverage;
import com.innopolis.innometrics.agentsgateway.service.SonarQubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("sonarqube")
public class SonarQubeController {

    @Autowired
    SonarQubeService sonarQubeService;


    @GetMapping("coverage")
    public Coverage getLastCoverage(@RequestParam Long projectAnalysisid){
        return sonarQubeService.getLastCoverage(projectAnalysisid);
    }

    @GetMapping("metrics")
    public ArrayNode getMetrics(@RequestParam(required = false) List<Long> projectsIds){
        return sonarQubeService.getMetrics(projectsIds);
    }

    @GetMapping("metric/history")
    public JsonNode getHistoryForMetric(@RequestParam String type, @RequestParam Long id) throws Exception {
        return sonarQubeService.getHistroyOfMetric(type,id);
    }
}
