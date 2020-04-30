package com.innopolis.innometrics.agentsgateway.controller;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.service.AgentconfigService;
import com.innopolis.innometrics.agentsgateway.service.AgentsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/AgentGateway", produces = MediaType.APPLICATION_JSON_VALUE)
public class AgentGateway {
    @Autowired
    AgentconfigService agentconfigService;

    @Autowired
    AgentsHandler agentsHandler;

    @GetMapping("/AgentList")
    public ResponseEntity<AgentListResponse> getAgentList() {
        AgentListResponse response = agentconfigService.getAgentList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/AgentConfiguration")
    public ResponseEntity<AgentConfigResponse> getAgentConfiguration(@RequestParam Integer AgentID, @RequestParam(required = false) String CallType) {
        AgentConfigResponse response = agentconfigService.getAgentConfig(AgentID, CallType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/projectList")
    public ResponseEntity<ProjectListResponse> getProjectList(@RequestBody ProjectListRequest request,
                                                              UriComponentsBuilder ucBuilder) {

        ProjectListResponse response = null;
        try {
            response = agentsHandler.getProjectList(request);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/connectProject")
    public ResponseEntity<?> getConnectProject(@RequestBody ConnectProjectRequest request,
                                               UriComponentsBuilder ucBuilder) {

        Boolean response = false;
        try {
            response = agentsHandler.getConnectProject(request);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (response)
            return new ResponseEntity<>(response, HttpStatus.OK);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/projectData")
    public ResponseEntity<RepoDataPerProjectResponse> getProjectData(@RequestParam Integer ProjectId) {

        RepoDataPerProjectResponse response = agentsHandler.getRepoDataPerProject(ProjectId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
