package com.innopolis.innometrics.agentsgateway.controller;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.entity.*;
import com.innopolis.innometrics.agentsgateway.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/AgentAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class AgentAdminController {

    @Autowired
    AgentconfigService agentconfigService;
    @Autowired
    AgentdataconfigService agentdataconfigService;
    @Autowired
    AgentconfigmethodsService agentconfigmethodsService;
    @Autowired
    AgentconfigdetailsService agentconfigdetailsService;
    @Autowired
    AgentresponseconfigService agentresponseconfigService;

    @GetMapping("/AgentConfiguration")
    public ResponseEntity<AgentConfigResponse> getAgentConfiguration(@RequestParam Integer AgentID,
                                                                     @RequestParam(required = false) String CallType) {
        AgentConfigResponse response = this.agentconfigService.getAgentConfig(AgentID, CallType);
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
    public ResponseEntity<AgentResponse> getAgentById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentconfig agentconfig = this.agentconfigService.getAgentById(id);
        if (agentconfig == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToAgentResponseDto(agentconfig), HttpStatus.OK);
    }

    @PostMapping(value = "/Agent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> postAgent(@RequestBody AgentResponse agent) {
        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfig agentconfig = this.convertToAgentconfigEntity(agent);
        Agentconfig response = this.agentconfigService.postAgent(agentconfig);
        return new ResponseEntity<>(this.convertToAgentResponseDto(response), HttpStatus.CREATED);
    }

    @PutMapping(value = "/Agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> putAgent(@PathVariable Integer id, @RequestBody AgentResponse agent) {
        if (id == null || agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfig agentconfig = this.convertToAgentconfigEntity(agent);
        Agentconfig response = this.agentconfigService.putAgent(id, agentconfig);
        if (response.getAgentid().equals(agent.getAgentid())) {
            return new ResponseEntity<>(this.convertToAgentResponseDto(response), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.convertToAgentResponseDto(response), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/Agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> deleteAgentById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfig response = this.agentconfigService.deleteAgentById(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToAgentResponseDto(response), HttpStatus.OK);
    }

    private AgentResponse convertToAgentResponseDto(Agentconfig agentConfig) {
        return new AgentResponse(
                agentConfig.getAgentid(),
                agentConfig.getAgentname(),
                agentConfig.getDescription(),
                agentConfig.getIsactive(),
                agentConfig.getCreationdate(),
                agentConfig.getCreatedby(),
                agentConfig.getLastupdate(),
                agentConfig.getUpdateby(),
                agentConfig.getSourcetype(),
                agentConfig.getDbschemasource(),
                agentConfig.getRepoidfield(),
                agentConfig.getOauthuri(),
                agentConfig.getAuthenticationmethod(),
                agentConfig.getAccesstokenendpoint(),
                agentConfig.getAuthorizationbaseurl(),
                agentConfig.getRequesttokenendpoint(),
                agentConfig.getApikey(),
                agentConfig.getApisecret()
        );
    }

    private Agentconfig convertToAgentconfigEntity(AgentResponse agentResponse) {
        Agentconfig agentconfig = new Agentconfig();

        agentconfig.setAgentname(agentResponse.getAgentname());
        agentconfig.setDescription(agentResponse.getDescription());
        agentconfig.setSourcetype(agentResponse.getSourcetype());
        agentconfig.setDbschemasource(agentResponse.getDbschemasource());
        agentconfig.setRepoidfield(agentResponse.getRepoidfield());
        agentconfig.setOauthuri(agentResponse.getOathuri());
        agentconfig.setAuthenticationmethod(agentResponse.getAuthenticationmethod());
        agentconfig.setApikey(agentResponse.getApikey());
        agentconfig.setApisecret(agentResponse.getApisecret());
        agentconfig.setAccesstokenendpoint(agentResponse.getAccesstokenendpoint());
        agentconfig.setAuthorizationbaseurl(agentResponse.getAuthorizationbaseurl());
        agentconfig.setRequesttokenendpoint(agentResponse.getRequesttokenendpoint());
        agentconfig.setIsactive(agentResponse.getIsconnected());

        return agentconfig;
    }

    /**
     * Table: agent_data_config
     */

    @GetMapping(value = "/AgentData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataListResponse> getDataList() {
        List<Agentdataconfig> dataList = this.agentdataconfigService.getDataList();
        if (dataList == null || dataList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DataListResponse response = new DataListResponse();
        for (Agentdataconfig data : dataList) {
            response.add(this.convertToDataConfigDto(data));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by datacofingid - get a method specifying its ID
    @GetMapping(value = "/AgentData/data/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataConfigDTO> getDataById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentdataconfig agentdataconfig = this.agentdataconfigService.getDataById(id);
        if (agentdataconfig == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DataConfigDTO response = this.convertToDataConfigDto(agentdataconfig);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by agentid - get all data config agents which belong to specified agentId
    @GetMapping(value = "/AgentData/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataListResponse> getDataByAgentId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Agentdataconfig> dataList = this.agentdataconfigService.getDataAgentsByAgentId(id);
        if (dataList == null || dataList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DataListResponse response = new DataListResponse();
        for (Agentdataconfig agentdataconfig : dataList) {
            response.add(this.convertToDataConfigDto(agentdataconfig));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/AgentData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataConfigDTO> postData(@RequestBody DataConfigDTO dataConfigDTO) {
        if (dataConfigDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentdataconfig agentdataconfig = this.convertToAgentdataconfigEntity(dataConfigDTO);
        if (agentdataconfig == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentdataconfig response = this.agentdataconfigService.postData(agentdataconfig);
        return new ResponseEntity<>(this.convertToDataConfigDto(response), HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataConfigDTO> putData(@PathVariable Integer id,
                                                 @RequestBody DataConfigDTO dataConfigDTO) {
        if (id == null || dataConfigDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentdataconfig agentdataconfig = this.convertToAgentdataconfigEntity(dataConfigDTO);
        if (agentdataconfig == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentdataconfig response = this.agentdataconfigService.putData(id, agentdataconfig);
        if (response.getDatacofingid().equals(dataConfigDTO.getDatacofingid())) {
            return new ResponseEntity<>(this.convertToDataConfigDto(response), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.convertToDataConfigDto(response), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/AgentData/data/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataConfigDTO> deleteDataById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentdataconfig response = this.agentdataconfigService.deleteDataById(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToDataConfigDto(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentData/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataListResponse> deleteDataByAgentId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Agentdataconfig> dataList = this.agentdataconfigService.deleteDataByAgentId(id);
        if (dataList == null || dataList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DataListResponse response = new DataListResponse();
        for (Agentdataconfig agentdataconfig : dataList) {
            response.add(this.convertToDataConfigDto(agentdataconfig));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private DataConfigDTO convertToDataConfigDto(Agentdataconfig agentdataconfig) {
        return new DataConfigDTO(
                agentdataconfig.getDatacofingid(),
                agentdataconfig.getAgentid(),
                agentdataconfig.getSchemaname(),
                agentdataconfig.getTablename(),
                agentdataconfig.getEventdatefield(),
                agentdataconfig.getEventauthorfield(),
                agentdataconfig.getEventdescriptionfield(),
                agentdataconfig.getIsactive(),
                agentdataconfig.getCreationdate(),
                agentdataconfig.getCreatedby(),
                agentdataconfig.getLastupdate(),
                agentdataconfig.getUpdateby(),
                agentdataconfig.getEventtype()
        );
    }

    private Agentdataconfig convertToAgentdataconfigEntity(DataConfigDTO dataConfigDTO) {

        Integer agentid = dataConfigDTO.getAgentid();
        if (agentid == null) {
            return null;
        }
        Agentconfig agentconfig = this.agentdataconfigService.getAgent(agentid);
        if (agentconfig == null) {
            return null;
        }

        Agentdataconfig agentdataconfig = new Agentdataconfig();
        agentdataconfig.setAgentid(agentid);
        agentdataconfig.setAgentConfig(agentconfig);
        agentdataconfig.setSchemaname(dataConfigDTO.getSchemaname());
        agentdataconfig.setTablename(dataConfigDTO.getTablename());
        agentdataconfig.setEventdatefield(dataConfigDTO.getEventdatefield());
        agentdataconfig.setEventauthorfield(dataConfigDTO.getEventauthorfield());
        agentdataconfig.setEventdescriptionfield(dataConfigDTO.getEventdescriptionfield());
        agentdataconfig.setEventtype(dataConfigDTO.getEventtype());
        agentdataconfig.setIsactive(dataConfigDTO.getIsactive());

        return agentdataconfig;
    }

    /**
     * Table: agentconfigmethods
     */

    @GetMapping(value = "/AgentConfigMethods", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodsListResponse> getMethodsList() {
        List<Agentconfigmethods> methodsList = this.agentconfigmethodsService.getMethodsList();
        if (methodsList == null || methodsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MethodsListResponse response = new MethodsListResponse();
        for (Agentconfigmethods agentconfigmethod : methodsList) {
            response.add(this.convertToMethodConfigDto(agentconfigmethod));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by methodid - get a method specifying its ID
    @GetMapping(value = "/AgentConfigMethods/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodConfigDTO> getMethodById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentconfigmethods agentconfigmethods = this.agentconfigmethodsService.getMethodById(id);
        if (agentconfigmethods == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MethodConfigDTO response = this.convertToMethodConfigDto(agentconfigmethods);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by agentid - get all methods which belong to specified agentId
    @GetMapping(value = "/AgentConfigMethods/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodsListResponse> getMethodsByAgentId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Agentconfigmethods> methodsList = this.agentconfigmethodsService.getMethodsByAgentId(id);
        if (methodsList == null || methodsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MethodsListResponse response = new MethodsListResponse();
        for (Agentconfigmethods agentconfigmethod : methodsList) {
            response.add(this.convertToMethodConfigDto(agentconfigmethod));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/AgentConfigMethodsOperation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodConfigDTO> getMethodsByAgentidAndOperation(@PathVariable Integer id,
                                                                           @RequestParam(value = "operation") String operation) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentconfigmethods response = this.agentconfigmethodsService.getMethodsByAgentidAndOperation(id, operation);

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToMethodConfigDto(response), HttpStatus.OK);
    }

    @PostMapping(value = "/AgentConfigMethods", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodConfigDTO> postMethod(@RequestBody MethodConfigDTO method) {
        if (method == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigmethods agentconfigmethods = this.convertToAgentconfigmethodsEntity(method);
        if (agentconfigmethods == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigmethods response = this.agentconfigmethodsService.postMethod(agentconfigmethods);
        return new ResponseEntity<>(this.convertToMethodConfigDto(response), HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentConfigMethods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodConfigDTO> putMethod(@PathVariable Integer id, @RequestBody MethodConfigDTO method) {
        if (id == null || method == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigmethods agentconfigmethods = this.convertToAgentconfigmethodsEntity(method);
        if (agentconfigmethods == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigmethods response = this.agentconfigmethodsService.putMethod(id, agentconfigmethods);
        if (response.getMethodid().equals(method.getMethodid())) {
            return new ResponseEntity<>(this.convertToMethodConfigDto(response), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.convertToMethodConfigDto(response), HttpStatus.CREATED);
    }

    // Delete by methodid - delete a method specifying its ID
    @DeleteMapping(value = "/AgentConfigMethods/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodConfigDTO> deleteMethodsById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentconfigmethods agentconfigmethod = this.agentconfigmethodsService.deleteMethodById(id);
        if (agentconfigmethod == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MethodConfigDTO response = this.convertToMethodConfigDto(agentconfigmethod);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete by agentid - delete all methods which belong to specified agentId
    @DeleteMapping(value = "/AgentConfigMethods/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodsListResponse> deleteMethodsByAgentId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Agentconfigmethods> methodsList = this.agentconfigmethodsService.deleteMethodsByAgentId(id);
        if (methodsList == null || methodsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MethodsListResponse response = new MethodsListResponse();
        for (Agentconfigmethods agentconfigmethod : methodsList) {
            response.add(this.convertToMethodConfigDto(agentconfigmethod));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private MethodConfigDTO convertToMethodConfigDto(Agentconfigmethods agentconfigmethod) {
        MethodConfigDTO methodConfig = new MethodConfigDTO();

        methodConfig.setMethodid(agentconfigmethod.getMethodid());
        methodConfig.setAgentid(agentconfigmethod.getAgentid());
        methodConfig.setEndpoint(agentconfigmethod.getEndpoint());
        methodConfig.setDescription(agentconfigmethod.getDescription());
        methodConfig.setOperation(agentconfigmethod.getOperation());
        methodConfig.setIsactive(agentconfigmethod.getIsactive());
        methodConfig.setCreationdate(agentconfigmethod.getCreationdate());
        methodConfig.setCreatedby(agentconfigmethod.getCreatedby());
        methodConfig.setLastupdate(agentconfigmethod.getLastupdate());
        methodConfig.setUpdateby(agentconfigmethod.getUpdateby());
        methodConfig.setRequesttype(agentconfigmethod.getRequesttype());

        List<ParamsConfigDTO> parameters = new ArrayList<>();
        for (Agentconfigdetails agentconfigdetails : agentconfigmethod.getParams()) {
            ParamsConfigDTO paramsConfigDTO = new ParamsConfigDTO();
            paramsConfigDTO.setParamname(agentconfigdetails.getParamname());
            paramsConfigDTO.setParamtype(agentconfigdetails.getParamtype());
            parameters.add(paramsConfigDTO);
        }
        methodConfig.setConfigParameters(parameters);

        // todo what about Response Parameters?
        // Should we create ParamsResponseDTO?


        return methodConfig;
    }

    private Agentconfigmethods convertToAgentconfigmethodsEntity(MethodConfigDTO methodConfigDTO) {

        Integer agentid = methodConfigDTO.getAgentid();
        if (agentid == null) {
            return null;
        }
        Agentconfig agentconfig = this.agentconfigService.getAgentById(agentid);
        if (agentconfig == null) {
            return null;
        }

        Agentconfigmethods agentconfigmethods = new Agentconfigmethods();

        agentconfigmethods.setAgentid(agentid);
        agentconfigmethods.setAgentConfig(agentconfig);
        agentconfigmethods.setEndpoint(methodConfigDTO.getEndpoint());
        agentconfigmethods.setDescription(methodConfigDTO.getDescription());
        agentconfigmethods.setOperation(methodConfigDTO.getOperation());
        agentconfigmethods.setIsactive(methodConfigDTO.getIsactive());
        agentconfigmethods.setRequesttype(methodConfigDTO.getRequesttype());

        List<ParamsConfigDTO> parameters = methodConfigDTO.getConfigParameters();
        if (parameters != null) {
            Set<Agentconfigdetails> params = new HashSet<>();
            for (ParamsConfigDTO paramsConfigDTO : parameters) {
                // maybe we shouldn't create empty Agentconfigdetails,
                // but get by id which is already exist? For example:
                // List<Agentconfigdetails> configDetailsList = this.agentconfigdetailsService.getDetailsByMethodId(id);
                Agentconfigdetails agentconfigdetails = new Agentconfigdetails();
                agentconfigdetails.setParamname(paramsConfigDTO.getParamname());
                agentconfigdetails.setParamtype(paramsConfigDTO.getParamtype());
                params.add(agentconfigdetails);
            }
            agentconfigmethods.setParams(params);
        }

        // todo what about Response Parameters?

        return agentconfigmethods;
    }

    /**
     * Table: agentconfigdetails
     */

    @GetMapping(value = "/AgentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsListResponse> getDetailsList() {
        List<Agentconfigdetails> detailsList = this.agentconfigdetailsService.getDetailsList();
        if (detailsList == null || detailsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DetailsListResponse response = new DetailsListResponse();
        for (Agentconfigdetails data : detailsList) {
            response.add(this.convertToDetailsConfigDto(data));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by configDetId - get config details specifying its ID
    @GetMapping(value = "/AgentDetails/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsConfigDTO> getDetailsById(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentconfigdetails agentconfigdetails = this.agentconfigdetailsService.getDetailsById(id);
        if (agentconfigdetails == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DetailsConfigDTO responseList = this.convertToDetailsConfigDto(agentconfigdetails);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // Get by methodid - get all config details which belong to specified methodid
    @GetMapping(value = "/AgentDetails/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsListResponse> getDetailsByMethodId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Agentconfigdetails> detailsList = this.agentconfigdetailsService.getDetailsByMethodId(id);
        if (detailsList == null || detailsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DetailsListResponse responseList = new DetailsListResponse();
        for (Agentconfigdetails agentconfigdetails : detailsList) {
            responseList.add(this.convertToDetailsConfigDto(agentconfigdetails));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping(value = "/AgentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsConfigDTO> postDetails(@RequestBody DetailsConfigDTO detailsConfigDTO) {
        if (detailsConfigDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails agentconfigdetails = this.convertToAgentconfigdetailsEntity(detailsConfigDTO);
        if (agentconfigdetails == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails response = this.agentconfigdetailsService.postDetails(agentconfigdetails);
        return new ResponseEntity<>(this.convertToDetailsConfigDto(response), HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsConfigDTO> putDetails(@PathVariable Integer id,
                                                       @RequestBody DetailsConfigDTO detailsConfigDTO) {
        if (id == null || detailsConfigDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails agentconfigdetails = this.convertToAgentconfigdetailsEntity(detailsConfigDTO);
        if (agentconfigdetails == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails response = this.agentconfigdetailsService.putDetails(id, agentconfigdetails);
        if (response.getConfigDetId().equals(detailsConfigDTO.getConfigDetId())) {
            return new ResponseEntity<>(this.convertToDetailsConfigDto(response), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.convertToDetailsConfigDto(response), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/AgentDetails/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsConfigDTO> deleteDetailsById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails response = this.agentconfigdetailsService.deleteDetailsById(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToDetailsConfigDto(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentDetails/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsListResponse> deleteDetailsByMethodId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Agentconfigdetails> detailsList = this.agentconfigdetailsService.deleteDetailsByMethodId(id);

        if (detailsList == null || detailsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DetailsListResponse response = new DetailsListResponse();
        for (Agentconfigdetails agentconfigdetails : detailsList) {
            response.add(this.convertToDetailsConfigDto(agentconfigdetails));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private DetailsConfigDTO convertToDetailsConfigDto(Agentconfigdetails agentconfigdetails) {
        return new DetailsConfigDTO(
                agentconfigdetails.getConfigDetId(),
                agentconfigdetails.getAgentconfigmethods().getMethodid(),
                agentconfigdetails.getParamname(),
                agentconfigdetails.getParamtype(),
                agentconfigdetails.getRequestparam(),
                agentconfigdetails.getRequesttype(),
                agentconfigdetails.getIsactive(),
                agentconfigdetails.getDefaultvalue(),
                agentconfigdetails.getCreationdate(),
                agentconfigdetails.getCreatedby(),
                agentconfigdetails.getLastupdate(),
                agentconfigdetails.getUpdateby()
        );
    }

    private Agentconfigdetails convertToAgentconfigdetailsEntity(DetailsConfigDTO detailsConfigDTO) {

        Integer methodid = detailsConfigDTO.getMethodid();
        if (methodid == null) {
            return null;
        }
        Agentconfigmethods agentconfigmethods = this.agentconfigdetailsService.getMethod(methodid);
        if (agentconfigmethods == null) {
            return null;
        }

        Agentconfigdetails agentconfigdetails = new Agentconfigdetails();

        agentconfigdetails.setAgentconfigmethods(agentconfigmethods);
        agentconfigdetails.setParamname(detailsConfigDTO.getParamname());
        agentconfigdetails.setParamtype(detailsConfigDTO.getParamtype());
        agentconfigdetails.setRequestparam(detailsConfigDTO.getRequestparam());
        agentconfigdetails.setRequesttype(detailsConfigDTO.getRequesttype());
        agentconfigdetails.setIsactive(detailsConfigDTO.getIsactive());
        agentconfigdetails.setDefaultvalue(detailsConfigDTO.getDefaultvalue());

        return agentconfigdetails;
    }

    /**
     * Table: agentresponseconfig
     */

    @GetMapping(value = "/AgentResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseListResponse> getResponsesList() {
        List<Agentresponseconfig> responseList = this.agentresponseconfigService.getResponsesList();
        if (responseList == null || responseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseListResponse response = new ResponseListResponse();
        for (Agentresponseconfig responseConfig : responseList) {
            response.add(this.convertToResponseConfigDto(responseConfig));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by configresponseid - get response details specifying its ID
    @GetMapping(value = "/AgentResponse/response/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseConfigDTO> getResponseById(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentresponseconfig agentresponseconfig = this.agentresponseconfigService.getResponseById(id);
        if (agentresponseconfig == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseConfigDTO responseList = this.convertToResponseConfigDto(agentresponseconfig);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // Get by methodid - get all config details which belong to specified methodid
    @GetMapping(value = "/AgentResponse/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseListResponse> getResponsesByMethodId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Agentresponseconfig> responsesList = this.agentresponseconfigService.getResponsesByMethodId(id);
        if (responsesList == null || responsesList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseListResponse responseList = new ResponseListResponse();
        for (Agentresponseconfig agentresponseconfig : responsesList) {
            responseList.add(this.convertToResponseConfigDto(agentresponseconfig));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping(value = "/AgentResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseConfigDTO> postResponse(@RequestBody ResponseConfigDTO responseConfigDTO) {
        if (responseConfigDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentresponseconfig agentresponseconfig = this.convertToAgentresponseconfigEntity(responseConfigDTO);
        if (agentresponseconfig == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentresponseconfig response = this.agentresponseconfigService.postResponse(agentresponseconfig);
        return new ResponseEntity<>(this.convertToResponseConfigDto(response), HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentResponse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseConfigDTO> putResponse(@PathVariable Integer id,
                                                         @RequestBody ResponseConfigDTO responseConfigDTO) {
        if (id == null || responseConfigDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentresponseconfig agentresponseconfig = this.convertToAgentresponseconfigEntity(responseConfigDTO);
        if (agentresponseconfig == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentresponseconfig response = this.agentresponseconfigService.putResponse(id, agentresponseconfig);
        if (response.getConfigresponseid().equals(responseConfigDTO.getConfigresponseid())) {
            return new ResponseEntity<>(this.convertToResponseConfigDto(response), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.convertToResponseConfigDto(response), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/AgentResponse/response/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseConfigDTO> deleteResponseById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentresponseconfig response = this.agentresponseconfigService.deleteResponseById(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToResponseConfigDto(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentResponse/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseListResponse> deleteResponseByMethodId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Agentresponseconfig> responseList = this.agentresponseconfigService.deleteResponseByMethodId(id);
        if (responseList == null || responseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseListResponse response = new ResponseListResponse();
        for (Agentresponseconfig agentresponseconfig : responseList) {
            response.add(this.convertToResponseConfigDto(agentresponseconfig));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseConfigDTO convertToResponseConfigDto(Agentresponseconfig agentresponseconfig) {
        return new ResponseConfigDTO(
                agentresponseconfig.getConfigresponseid(),
                agentresponseconfig.getAgentconfigmethods().getMethodid(),
                agentresponseconfig.getResponseparam(),
                agentresponseconfig.getParamname(),
                agentresponseconfig.getParamtype(),
                agentresponseconfig.getIsactive(),
                agentresponseconfig.getCreationdate(),
                agentresponseconfig.getCreatedby(),
                agentresponseconfig.getLastupdate(),
                agentresponseconfig.getUpdateby()
        );
    }

    private Agentresponseconfig convertToAgentresponseconfigEntity(ResponseConfigDTO responseConfigDTO) {

        Integer methodid = responseConfigDTO.getMethodid();
        if (methodid == null) {
            return null;
        }
        Agentconfigmethods agentconfigmethods = this.agentresponseconfigService.getMethod(methodid);
        if (agentconfigmethods == null) {
            return null;
        }

        Agentresponseconfig agentresponseconfig = new Agentresponseconfig();

        agentresponseconfig.setAgentconfigmethods(agentconfigmethods);
        agentresponseconfig.setResponseparam(responseConfigDTO.getResponseparam());
        agentresponseconfig.setParamname(responseConfigDTO.getParamname());
        agentresponseconfig.setParamtype(responseConfigDTO.getParamtype());
        agentresponseconfig.setIsactive(responseConfigDTO.getIsactive());

        return agentresponseconfig;
    }
}
