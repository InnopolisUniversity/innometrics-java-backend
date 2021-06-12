package com.innopolis.innometrics.agentsgateway.controller;


import com.innopolis.innometrics.agentsgateway.DTO.AgentConfigResponse;
import com.innopolis.innometrics.agentsgateway.DTO.AgentListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.AgentResponse;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.service.AgentconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/AgentAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class AgentAdminController {

    @Autowired
    AgentconfigService agentconfigService;


    @GetMapping("/AgentList")
    public ResponseEntity<AgentListResponse> getAgentList(@RequestParam Integer ProjectId) {
        AgentListResponse response = agentconfigService.getAgentList(ProjectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/Agent/{id}")
    public ResponseEntity<AgentResponse> getAgent(@PathVariable Integer id) {
        AgentResponse response = agentconfigService.getAgent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/Agent/{id}")
    public ResponseEntity<AgentResponse> putAgent(@PathVariable Integer id, @RequestBody Agentconfig agent) {
        AgentResponse response = agentconfigService.putAgent(id, agent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/Agent")
    public ResponseEntity<AgentResponse> postAgent(@RequestBody Agentconfig agent) {
        AgentResponse response = agentconfigService.postAgent(agent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/Agent/{id}")
    public ResponseEntity<AgentResponse> deleteAgent(@PathVariable Integer id) {
        AgentResponse response = agentconfigService.deleteAgent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/AgentConfiguration")
    public ResponseEntity<AgentConfigResponse> getAgentConfiguration(@RequestParam Integer AgentID,
                                                                     @RequestParam(required = false) String CallType) {
        AgentConfigResponse response = agentconfigService.getAgentConfig(AgentID, CallType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
