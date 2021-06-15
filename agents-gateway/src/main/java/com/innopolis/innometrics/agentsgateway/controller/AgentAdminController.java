package com.innopolis.innometrics.agentsgateway.controller;

import com.innopolis.innometrics.agentsgateway.DTO.AgentConfigResponse;
import com.innopolis.innometrics.agentsgateway.DTO.AgentListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.AgentResponse;
import com.innopolis.innometrics.agentsgateway.DTO.MethodConfigDTO;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/AgentAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class AgentAdminController {

    @Autowired
    AgentconfigService agentconfigService;
    @Autowired
    AgentconfigmethodsService agentconfigmethodsService;
    @Autowired
    AgentconfigdetailsService agentconfigdetailsService;
    @Autowired
    AgentdataconfigService agentdataconfigService;
    @Autowired
    AgentresponseconfigService agentresponseconfigService;

    @GetMapping("/AgentConfiguration")
    public ResponseEntity<AgentConfigResponse> getAgentConfiguration(@RequestParam Integer AgentID,
                                                                     @RequestParam(required = false) String CallType) {
        AgentConfigResponse response = agentconfigService.getAgentConfig(AgentID, CallType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Table: agentconfig
     */

    @GetMapping(value = "/Agent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentListResponse> getAgentList(@RequestParam Integer ProjectId) {
        AgentListResponse response = this.agentconfigService.getAgentList(ProjectId);
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/Agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> getAgent(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AgentResponse response = this.agentconfigService.getAgent(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/Agent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> postAgent(@RequestBody Agentconfig agent) {
        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AgentResponse response = agentconfigService.postAgent(agent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/Agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> putAgent(@PathVariable Integer id, @RequestBody Agentconfig agent) {
        if (id == null || agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AgentResponse response = agentconfigService.putAgent(id, agent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/Agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> deleteAgent(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AgentResponse response = agentconfigService.deleteAgent(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Table: agentconfigmethods
     */

    @GetMapping(value = "/AgentConfigMethods", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MethodConfigDTO>> getMethodsList() {
        List<MethodConfigDTO> response = this.agentconfigmethodsService.getMethodsList();
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/AgentConfigMethods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MethodConfigDTO>> getMethod(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<MethodConfigDTO> response = this.agentconfigmethodsService.getMethod(id);
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/AgentConfigMethods", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodConfigDTO> postMethod(@RequestBody Agentconfigmethods method) {
        if (method == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MethodConfigDTO response = agentconfigmethodsService.postMethod(method);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentConfigMethods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodConfigDTO> putMethod(@PathVariable Integer id, @RequestBody Agentconfigmethods method) {
        if (id == null || method == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MethodConfigDTO response = agentconfigmethodsService.putMethod(id, method);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentConfigMethods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MethodConfigDTO>> deleteMethod(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<MethodConfigDTO> response = agentconfigmethodsService.deleteMethod(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Table: agentconfigdetails
     */
    // todo write Request Mappings for agentconfigdetails table
  /*
    @GetMapping(value = "/AgentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<AgentListResponse> getDetailsList() {
        AgentListResponse response = this.agentconfigdetailsService.getAgentConfigDetailsList();
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/AgentDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> getDetails(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AgentResponse response = this.agentconfigdetailsService.getConfigDetails(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/AgentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> postDetails(@RequestBody Agentconfig agent) {
        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AgentResponse response = agentconfigdetailsService.postConfigDetails(agent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> putDetails(@PathVariable Integer id, @RequestBody Agentconfig agent) {
        if (id == null || agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AgentResponse response = agentconfigdetailsService.putConfigDetails(id, agent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> deleteDetails(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AgentResponse response = agentconfigdetailsService.deleteConfigDetails(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
     */


    /**
     * Table: agent_data_config
     */
// todo write Request Mappings for agent_data_config table
/*
    @GetMapping(value = "/AgentData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentListResponse> getDataList() {
        AgentListResponse response = this.agentdataconfigService.getDataConfigList();
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/AgentData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> getData(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AgentResponse response = this.agentdataconfigService.getDataConfig(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/AgentData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> postData(@RequestBody Agentconfig agent) {
        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AgentResponse response = agentdataconfigService.postDataConfig(agent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> putData(@PathVariable Integer id, @RequestBody Agentconfig agent) {
        if (id == null || agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AgentResponse response = agentdataconfigService.putDataConfig(id, agent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> deleteData(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AgentResponse response = agentdataconfigService.deleteDataConfig(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     */

    /**
     * Table: agentresponseconfig
     */
// todo write Request Mappings for agentresponseconfig table
/*
    @GetMapping(value = "/AgentResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentListResponse> getResponsesList() {
        AgentListResponse response = this.agentresponseconfigService.getResponseConfigList();
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/AgentResponse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> getResponse(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AgentResponse response = this.agentresponseconfigService.getResponseConfig(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/AgentResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> postResponse(@RequestBody Agentconfig agent) {
        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AgentResponse response = agentresponseconfigService.postResponseConfig(agent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentResponse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> putResponse(@PathVariable Integer id, @RequestBody Agentconfig agent) {
        if (id == null || agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AgentResponse response = agentresponseconfigService.putResponseConfig(id, agent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentResponse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> deleteResponse(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AgentResponse response = agentresponseconfigService.deleteResponseConfig(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     */
}
