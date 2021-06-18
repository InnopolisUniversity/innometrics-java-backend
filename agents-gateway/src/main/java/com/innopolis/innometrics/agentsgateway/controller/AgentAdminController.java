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

        Agentconfig response = this.agentconfigService.getAgent(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToAgentResponseDto(response), HttpStatus.OK);
    }

    @PostMapping(value = "/Agent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> postAgent(@RequestBody AgentResponse agent) {
        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfig agentconfig = this.convertToAgentconfigEntity(agent);
        Agentconfig response = agentconfigService.postAgent(agentconfig);

        return new ResponseEntity<>(this.convertToAgentResponseDto(response), HttpStatus.CREATED);
    }

    @PutMapping(value = "/Agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> putAgent(@PathVariable Integer id, @RequestBody AgentResponse agent) {
        if (id == null || agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfig agentconfig = this.convertToAgentconfigEntity(agent);
        Agentconfig response = agentconfigService.putAgent(id, agentconfig);

        return new ResponseEntity<>(this.convertToAgentResponseDto(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/Agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> deleteAgent(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfig response = agentconfigService.deleteAgent(id);
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
        //todo what about date?

        return agentconfig;
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

    @GetMapping(value = "/AgentConfigMethods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodsListResponse> getMethodsById(@PathVariable Integer id,
                                                              @RequestParam(value = "id_type") String idType) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        switch (idType.toLowerCase()) {
            // Get by agentid - get all methods which belong to specified agentId
            case "agent":
                List<Agentconfigmethods> methodsList = this.agentconfigmethodsService.getMethodsByAgentId(id);

                if (methodsList == null || methodsList.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                MethodsListResponse response = new MethodsListResponse();
                for (Agentconfigmethods agentconfigmethod : methodsList) {
                    response.add(this.convertToMethodConfigDto(agentconfigmethod));
                }

                return new ResponseEntity<>(response, HttpStatus.OK);

            // Get by methodid - get a method specifying its ID
            case "method":
                Agentconfigmethods method = agentconfigmethodsService.getMethodByMethodId(id);
                if (method == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                MethodsListResponse responseMethod = new MethodsListResponse();
                responseMethod.add(this.convertToMethodConfigDto(method));
                return new ResponseEntity<>(responseMethod, HttpStatus.OK);
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
        Agentconfigmethods response = agentconfigmethodsService.postMethod(agentconfigmethods);

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
        Agentconfigmethods response = agentconfigmethodsService.putMethod(id, agentconfigmethods);
        return new ResponseEntity<>(this.convertToMethodConfigDto(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentConfigMethods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodsListResponse> deleteMethods(@PathVariable Integer id,
                                                             @RequestParam(value = "id_type") String idType) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        switch (idType.toLowerCase()) {
            // Delete by agentid - delete all methods which belong to specified agentId
            case "agent":
                List<Agentconfigmethods> methodsList = agentconfigmethodsService.deleteMethodsByAgentId(id);
                if (methodsList == null || methodsList.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                MethodsListResponse response = new MethodsListResponse();
                for (Agentconfigmethods agentconfigmethod : methodsList) {
                    response.add(this.convertToMethodConfigDto(agentconfigmethod));
                }
                return new ResponseEntity<>(response, HttpStatus.OK);

            // Delete by methodid - delete a method specifying its ID
            case "method":
                Agentconfigmethods method = agentconfigmethodsService.deleteMethodByMethodId(id);
                if (method == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                MethodsListResponse responseMethod = new MethodsListResponse();
                responseMethod.add(this.convertToMethodConfigDto(method));
                return new ResponseEntity<>(responseMethod, HttpStatus.OK);
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
        methodConfig.setParameters(parameters);

        // todo what about Response Parameters?
        // Should we create ParamsResponseDTO?

        return methodConfig;
    }

    private Agentconfigmethods convertToAgentconfigmethodsEntity(MethodConfigDTO methodConfigDTO) {

        Integer agentid = methodConfigDTO.getAgentid();
        if (agentid == null) {
            return null;
        }
        Agentconfig agentconfig = agentconfigService.getAgent(agentid);
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
        //todo what about date?

        List<ParamsConfigDTO> parameters = methodConfigDTO.getParameters();
        if (parameters != null) {
            Set<Agentconfigdetails> params = new HashSet<>();
            for (ParamsConfigDTO paramsConfigDTO : parameters) {
                // todo fix parameters getter
                // maybe we shouldn't create empty Agentconfigdetails,
                // but get by id which is already exist? Should we care of parameters?
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
     * Table: agent_data_config
     */

    @GetMapping(value = "/AgentData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataListResponse> getDataList() {
        List<Agentdataconfig> dataList = this.agentdataconfigService.getDataConfigList();
        if (dataList == null || dataList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DataListResponse response = new DataListResponse();
        for (Agentdataconfig data : dataList) {
            response.add(this.convertToDataConfigDto(data));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/AgentData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataConfigDTO> getData(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentdataconfig response = this.agentdataconfigService.getDataConfig(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.convertToDataConfigDto(response), HttpStatus.OK);
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
        Agentdataconfig response = agentdataconfigService.postDataConfig(agentdataconfig);

        return new ResponseEntity<>(this.convertToDataConfigDto(response), HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataConfigDTO> putData(@PathVariable Integer id, @RequestBody DataConfigDTO dataConfigDTO) {
        if (id == null || dataConfigDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentdataconfig agentdataconfig = this.convertToAgentdataconfigEntity(dataConfigDTO);
        if (agentdataconfig == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentdataconfig response = agentdataconfigService.putDataConfig(id, agentdataconfig);
        return new ResponseEntity<>(this.convertToDataConfigDto(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentData/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataConfigDTO> deleteData(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentdataconfig response = agentdataconfigService.deleteDataConfig(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToDataConfigDto(response), HttpStatus.OK);
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
        Agentconfig agentconfig = agentdataconfigService.getAgent(agentid);
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
        //todo what about date?

        return agentdataconfig;
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

    @GetMapping(value = "/AgentDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsConfigDTO> getDetails(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentconfigdetails response = this.agentconfigdetailsService.getConfigDetails(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.convertToDetailsConfigDto(response), HttpStatus.OK);
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
        Agentconfigdetails response = agentconfigdetailsService.postConfigDetails(agentconfigdetails);

        return new ResponseEntity<>(this.convertToDetailsConfigDto(response), HttpStatus.CREATED);
    }

    @PutMapping(value = "/AgentDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsConfigDTO> putDetails(@PathVariable Integer id, @RequestBody DetailsConfigDTO detailsConfigDTO) {
        if (id == null || detailsConfigDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails agentconfigdetails = this.convertToAgentconfigdetailsEntity(detailsConfigDTO);
        if (agentconfigdetails == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails response = agentconfigdetailsService.putConfigDetails(id, agentconfigdetails);
        return new ResponseEntity<>(this.convertToDetailsConfigDto(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsConfigDTO> deleteDetails(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails response = agentconfigdetailsService.deleteConfigDetails(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToDetailsConfigDto(response), HttpStatus.OK);
    }

    private DetailsConfigDTO convertToDetailsConfigDto(Agentconfigdetails agentconfigdetails) {
        return new DetailsConfigDTO(
                agentconfigdetails.getConfigDetId(),
                //agentconfigdetails.getMethodid(),
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
        Agentconfigmethods agentconfigmethods = agentconfigdetailsService.getMethod(methodid);
        if (agentconfigmethods == null) {
            return null;
        }

        Agentconfigdetails agentconfigdetails = new Agentconfigdetails();

        //agentconfigdetails.setMethodid(methodid);
        agentconfigdetails.setAgentconfigmethods(agentconfigmethods);
        agentconfigdetails.setParamname(detailsConfigDTO.getParamname());
        agentconfigdetails.setParamtype(detailsConfigDTO.getParamtype());
        agentconfigdetails.setRequestparam(detailsConfigDTO.getRequestparam());
        agentconfigdetails.setRequesttype(detailsConfigDTO.getRequesttype());
        agentconfigdetails.setIsactive(detailsConfigDTO.getIsactive());
        agentconfigdetails.setDefaultvalue(detailsConfigDTO.getDefaultvalue());
        //todo what about date?

        return agentconfigdetails;
    }

    /**
     * Table: agentresponseconfig
     */

    @GetMapping(value = "/AgentResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseListResponse> getResponsesList() {
        List<Agentresponseconfig> responseList = this.agentresponseconfigService.getResponseList();
        if (responseList == null || responseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseListResponse response = new ResponseListResponse();
        for (Agentresponseconfig responseConfig : responseList) {
            response.add(this.convertToResponseConfigDto(responseConfig));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/AgentResponse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseConfigDTO> getResponse(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentresponseconfig response = this.agentresponseconfigService.getResponse(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.convertToResponseConfigDto(response), HttpStatus.OK);
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
        Agentresponseconfig response = agentresponseconfigService.postResponse(agentresponseconfig);

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
        Agentresponseconfig response = agentresponseconfigService.putResponse(id, agentresponseconfig);
        return new ResponseEntity<>(this.convertToResponseConfigDto(response), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentResponse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseConfigDTO> deleteResponse(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentresponseconfig response = agentresponseconfigService.deleteResponse(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.convertToResponseConfigDto(response), HttpStatus.OK);
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
        Agentconfigmethods agentconfigmethods = agentconfigdetailsService.getMethod(methodid);
        if (agentconfigmethods == null) {
            return null;
        }

        Agentresponseconfig agentresponseconfig = new Agentresponseconfig();

        agentresponseconfig.setAgentconfigmethods(agentconfigmethods);
        agentresponseconfig.setResponseparam(responseConfigDTO.getResponseparam());
        agentresponseconfig.setParamname(responseConfigDTO.getParamname());
        agentresponseconfig.setParamtype(responseConfigDTO.getParamtype());
        agentresponseconfig.setIsactive(responseConfigDTO.getIsactive());
        //todo what about date?

        return agentresponseconfig;
    }
}
