package com.innopolis.innometrics.agentsgateway.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innopolis.innometrics.agentsgateway.DTO.AgentConfigResponse;
import com.innopolis.innometrics.agentsgateway.DTO.AgentListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.AgentResponse;
import com.innopolis.innometrics.agentsgateway.DTO.AgentsCompanyDTO;
import com.innopolis.innometrics.agentsgateway.DTO.AgentsCompanyListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.AgentsProjectDTO;
import com.innopolis.innometrics.agentsgateway.DTO.AgentsProjectListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.DataConfigDTO;
import com.innopolis.innometrics.agentsgateway.DTO.DataListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.DetailsConfigDTO;
import com.innopolis.innometrics.agentsgateway.DTO.DetailsListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.ExternalProjectTeamDTO;
import com.innopolis.innometrics.agentsgateway.DTO.ExternalProjectTeamListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.MethodConfigDTO;
import com.innopolis.innometrics.agentsgateway.DTO.MethodsListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.ParamsConfigDTO;
import com.innopolis.innometrics.agentsgateway.DTO.ReposProjectDTO;
import com.innopolis.innometrics.agentsgateway.DTO.ReposProjectListResponse;
import com.innopolis.innometrics.agentsgateway.DTO.ResponseConfigDTO;
import com.innopolis.innometrics.agentsgateway.DTO.ResponseListResponse;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.entity.Agentdataconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentresponseconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentsxcompany;
import com.innopolis.innometrics.agentsgateway.entity.Agentsxproject;
import com.innopolis.innometrics.agentsgateway.entity.Externalprojectxteam;
import com.innopolis.innometrics.agentsgateway.entity.Reposxproject;
import com.innopolis.innometrics.agentsgateway.service.AgentconfigService;
import com.innopolis.innometrics.agentsgateway.service.AgentconfigdetailsService;
import com.innopolis.innometrics.agentsgateway.service.AgentconfigmethodsService;
import com.innopolis.innometrics.agentsgateway.service.AgentdataconfigService;
import com.innopolis.innometrics.agentsgateway.service.AgentresponseconfigService;
import com.innopolis.innometrics.agentsgateway.service.AgentsxcompanyService;
import com.innopolis.innometrics.agentsgateway.service.AgentsxprojectService;
import com.innopolis.innometrics.agentsgateway.service.ExternalprojectxteamService;
import com.innopolis.innometrics.agentsgateway.service.ReposxprojectService;

@RestController
@RequestMapping(value = "/AgentAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT,
        RequestMethod.DELETE })
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
    @Autowired
    ReposxprojectService reposxprojectService;
    @Autowired
    AgentsxprojectService agentsxprojectService;
    @Autowired
    ExternalprojectxteamService externalprojectxteamService;
    @Autowired
    AgentsxcompanyService agentsxcompanyService;

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
        return response == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/Agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> getAgentById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfig agentconfig = this.agentconfigService.getAgentById(id);
        return agentconfig == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToAgentResponseDto(agentconfig), HttpStatus.OK);
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
        return new ResponseEntity<>(
                this.convertToAgentResponseDto(response),
                response.getAgentid().equals(agent.getAgentid())
                        ? HttpStatus.OK
                        : HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/Agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentResponse> deleteAgentById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfig deletedAgent = this.agentconfigService.deleteAgentById(id);
        return deletedAgent == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToAgentResponseDto(deletedAgent), HttpStatus.OK);
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
                agentConfig.getApisecret());
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
        return this.convertToDataListResponseEntity(dataList);
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

        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentdataconfig> dataList = this.agentdataconfigService.getDataAgentsByAgentId(id);
        return this.convertToDataListResponseEntity(dataList);
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
        return new ResponseEntity<>(
                this.convertToDataConfigDto(response),
                response.getDatacofingid().equals(dataConfigDTO.getDatacofingid())
                        ? HttpStatus.OK
                        : HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/AgentData/data/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataConfigDTO> deleteDataById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentdataconfig deletedData = this.agentdataconfigService.deleteDataById(id);
        return deletedData == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToDataConfigDto(deletedData), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentData/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataListResponse> deleteDataByAgentId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentdataconfig> deletedDataList = this.agentdataconfigService.deleteDataByAgentId(id);
        return this.convertToDataListResponseEntity(deletedDataList);
    }

    private ResponseEntity<DataListResponse> convertToDataListResponseEntity(List<Agentdataconfig> dataList) {
        if (dataList == null) {
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
                agentdataconfig.getEventtype());
    }

    private Agentdataconfig convertToAgentdataconfigEntity(DataConfigDTO dataConfigDTO) {

        Integer agentid = dataConfigDTO.getAgentid();
        if (agentid == null) {
            return null;
        }
        Agentconfig agentconfig = this.agentconfigService.getAgentById(agentid);
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
        return this.convertToMethodsListResponseEntity(methodsList);
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

        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentconfigmethods> methodsList = this.agentconfigmethodsService.getMethodsByAgentId(id);
        return this.convertToMethodsListResponseEntity(methodsList);
    }

    @GetMapping(value = "/AgentConfigMethodsOperation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodConfigDTO> getMethodsByAgentidAndOperation(@PathVariable Integer id,
            @RequestParam(value = "operation") String operation) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigmethods response = this.agentconfigmethodsService.getMethodsByAgentidAndOperation(id, operation);
        return response == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToMethodConfigDto(response), HttpStatus.OK);
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
        return new ResponseEntity<>(
                this.convertToMethodConfigDto(response),
                response.getMethodid().equals(method.getMethodid())
                        ? HttpStatus.OK
                        : HttpStatus.CREATED);
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

        MethodConfigDTO deletedMethod = this.convertToMethodConfigDto(agentconfigmethod);
        return new ResponseEntity<>(deletedMethod, HttpStatus.OK);
    }

    // Delete by agentid - delete all methods which belong to specified agentId
    @DeleteMapping(value = "/AgentConfigMethods/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MethodsListResponse> deleteMethodsByAgentId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentconfigmethods> deletedMethodsList = this.agentconfigmethodsService.deleteMethodsByAgentId(id);
        return this.convertToMethodsListResponseEntity(deletedMethodsList);
    }

    private ResponseEntity<MethodsListResponse> convertToMethodsListResponseEntity(
            List<Agentconfigmethods> methodsList) {
        if (methodsList == null) {
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
                // List<Agentconfigdetails> configDetailsList =
                // this.agentconfigdetailsService.getDetailsByMethodId(id);
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
        return this.convertToDetailsListResponseEntity(detailsList);
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

        DetailsConfigDTO response = this.convertToDetailsConfigDto(agentconfigdetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by methodid - get all config details which belong to specified methodid
    @GetMapping(value = "/AgentDetails/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsListResponse> getDetailsByMethodId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.agentconfigmethodsService.getMethodById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentconfigdetails> detailsList = this.agentconfigdetailsService.getDetailsByMethodId(id);
        return this.convertToDetailsListResponseEntity(detailsList);
    }

    @PostMapping(value = "/AgentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsConfigDTO> postDetails(@RequestBody DetailsConfigDTO detailsConfigDTO) {
        if (detailsConfigDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails agentconfigdetails = this.convertToAgentconfigdetailsEntity(detailsConfigDTO);
        if (agentconfigdetails == null) {
            System.out.println("VRUN");
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
        return new ResponseEntity<>(
                this.convertToDetailsConfigDto(response),
                response.getConfigDetId().equals(detailsConfigDTO.getConfigDetId())
                        ? HttpStatus.OK
                        : HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/AgentDetails/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsConfigDTO> deleteDetailsById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentconfigdetails deletedDetails = this.agentconfigdetailsService.deleteDetailsById(id);
        return deletedDetails == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToDetailsConfigDto(deletedDetails), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentDetails/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsListResponse> deleteDetailsByMethodId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.agentconfigmethodsService.getMethodById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentconfigdetails> deletedDetailsList = this.agentconfigdetailsService.deleteDetailsByMethodId(id);
        return this.convertToDetailsListResponseEntity(deletedDetailsList);
    }

    private ResponseEntity<DetailsListResponse> convertToDetailsListResponseEntity(
            List<Agentconfigdetails> detailsList) {
        if (detailsList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DetailsListResponse response = new DetailsListResponse();
        for (Agentconfigdetails data : detailsList) {
            response.add(this.convertToDetailsConfigDto(data));
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
                agentconfigdetails.getUpdateby());
    }

    private Agentconfigdetails convertToAgentconfigdetailsEntity(DetailsConfigDTO detailsConfigDTO) {

        Integer methodid = detailsConfigDTO.getMethodid();
        if (methodid == null) {
            return null;
        }
        Agentconfigmethods agentconfigmethods = this.agentconfigmethodsService.getMethodById(methodid);
        if (agentconfigmethods == null) {
            System.out.println("VVV");
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
        return this.convertToResponsesListResponseEntity(responseList);
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

        ResponseConfigDTO response = this.convertToResponseConfigDto(agentresponseconfig);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by methodid - get all config details which belong to specified methodid
    @GetMapping(value = "/AgentResponse/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseListResponse> getResponsesByMethodId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.agentconfigmethodsService.getMethodById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentresponseconfig> responsesList = this.agentresponseconfigService.getResponsesByMethodId(id);
        return this.convertToResponsesListResponseEntity(responsesList);
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
        return new ResponseEntity<>(
                this.convertToResponseConfigDto(response),
                response.getConfigresponseid().equals(responseConfigDTO.getConfigresponseid())
                        ? HttpStatus.OK
                        : HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/AgentResponse/response/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseConfigDTO> deleteResponseById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentresponseconfig deletedResponse = this.agentresponseconfigService.deleteResponseById(id);
        return deletedResponse == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToResponseConfigDto(deletedResponse), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentResponse/method/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseListResponse> deleteResponseByMethodId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.agentconfigmethodsService.getMethodById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentresponseconfig> deletedResponseList = this.agentresponseconfigService.deleteResponseByMethodId(id);
        return this.convertToResponsesListResponseEntity(deletedResponseList);
    }

    private ResponseEntity<ResponseListResponse> convertToResponsesListResponseEntity(
            List<Agentresponseconfig> responsesList) {
        if (responsesList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseListResponse response = new ResponseListResponse();
        for (Agentresponseconfig responseConfig : responsesList) {
            response.add(this.convertToResponseConfigDto(responseConfig));
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
                agentresponseconfig.getUpdateby());
    }

    private Agentresponseconfig convertToAgentresponseconfigEntity(ResponseConfigDTO responseConfigDTO) {

        Integer methodid = responseConfigDTO.getMethodid();
        if (methodid == null) {
            return null;
        }
        Agentconfigmethods agentconfigmethods = this.agentconfigmethodsService.getMethodById(methodid);
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

    /**
     * Table: externalproject_x_team
     */

    @GetMapping(value = "/ExternalProjectTeam", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectTeamListResponse> getExternalProjectTeamList() {
        List<Externalprojectxteam> externalProjectTeamList = this.externalprojectxteamService
                .getExternalProjectTeamList();
        return this.convertToExternalProjectTeamListResponseEntity(externalProjectTeamList);
    }

    // Get by configid - get ExternalProjectTeam entry specifying its ID
    @GetMapping(value = "/ExternalProjectTeam/config/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectTeamDTO> getExternalProjectTeamById(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Externalprojectxteam externalprojectxteam = this.externalprojectxteamService.getExternalProjectTeamById(id);
        if (externalprojectxteam == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ExternalProjectTeamDTO response = this.convertToExternalProjectTeamDto(externalprojectxteam);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by agentid - get all ExternalProjectTeam entries which belong to
    // specified agentid
    @GetMapping(value = "/ExternalProjectTeam/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectTeamListResponse> getExternalProjectTeamByAgentId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Externalprojectxteam> externalProjectTeamList = this.externalprojectxteamService
                .getExternalProjectTeamByAgentId(id);
        return this.convertToExternalProjectTeamListResponseEntity(externalProjectTeamList);
    }

    // Get by teamid - get all ExternalProjectTeam entries which belong to specified
    // teamid
    @GetMapping(value = "/ExternalProjectTeam/team/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectTeamListResponse> getExternalProjectTeamByTeamId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // todo check if external Team entity with specified id exists

        List<Externalprojectxteam> externalProjectTeamList = this.externalprojectxteamService
                .getExternalProjectTeamByTeamId(id);
        return this.convertToExternalProjectTeamListResponseEntity(externalProjectTeamList);
    }

    @PostMapping(value = "/ExternalProjectTeam", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectTeamDTO> postExternalProjectTeam(
            @RequestBody ExternalProjectTeamDTO externalProjectTeamDTO) {
        if (externalProjectTeamDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Externalprojectxteam externalprojectxteam = this.convertToExternalprojectxteamEntity(externalProjectTeamDTO);
        if (externalprojectxteam == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Externalprojectxteam response = this.externalprojectxteamService
                    .postExternalProjectTeam(externalprojectxteam);
            return new ResponseEntity<>(this.convertToExternalProjectTeamDto(response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/ExternalProjectTeam/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectTeamDTO> putExternalProjectTeam(@PathVariable Integer id,
            @RequestBody ExternalProjectTeamDTO externalProjectTeamDTO) {
        if (id == null || externalProjectTeamDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Externalprojectxteam externalprojectxteam = this.convertToExternalprojectxteamEntity(externalProjectTeamDTO);
        if (externalprojectxteam == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Externalprojectxteam response = this.externalprojectxteamService.putExternalProjectTeam(id,
                    externalprojectxteam);
            return new ResponseEntity<>(
                    this.convertToExternalProjectTeamDto(response),
                    response.getConfigid().equals(externalProjectTeamDTO.getConfigid())
                            ? HttpStatus.OK
                            : HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/ExternalProjectTeam/config/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectTeamDTO> deleteExternalProjectTeamById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Externalprojectxteam deletedExternalProjectTeam = this.externalprojectxteamService
                .deleteExternalProjectTeamById(id);
        return deletedExternalProjectTeam == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToExternalProjectTeamDto(deletedExternalProjectTeam), HttpStatus.OK);
    }

    @DeleteMapping(value = "/ExternalProjectTeam/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectTeamListResponse> deleteExternalProjectTeamByAgentId(
            @PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Externalprojectxteam> deletedExternalProjectTeamList = this.externalprojectxteamService
                .deleteExternalProjectTeamByAgentId(id);
        return this.convertToExternalProjectTeamListResponseEntity(deletedExternalProjectTeamList);
    }

    @DeleteMapping(value = "/ExternalProjectTeam/team/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalProjectTeamListResponse> deleteExternalProjectTeamByTeamId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // todo check if external Team entity with specified id exists

        List<Externalprojectxteam> deletedExternalProjectTeamList = this.externalprojectxteamService
                .deleteExternalProjectTeamByTeamId(id);
        return this.convertToExternalProjectTeamListResponseEntity(deletedExternalProjectTeamList);
    }

    private ResponseEntity<ExternalProjectTeamListResponse> convertToExternalProjectTeamListResponseEntity(
            List<Externalprojectxteam> externalProjectTeamList) {
        if (externalProjectTeamList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ExternalProjectTeamListResponse responseList = new ExternalProjectTeamListResponse();
        for (Externalprojectxteam externalprojectxteam : externalProjectTeamList) {
            responseList.add(this.convertToExternalProjectTeamDto(externalprojectxteam));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    private ExternalProjectTeamDTO convertToExternalProjectTeamDto(Externalprojectxteam externalprojectxteam) {
        return new ExternalProjectTeamDTO(
                externalprojectxteam.getConfigid(),
                externalprojectxteam.getAgentid(),
                externalprojectxteam.getTeamid(),
                externalprojectxteam.getRepoid(),
                externalprojectxteam.getIsactive(),
                externalprojectxteam.getCreationdate(),
                externalprojectxteam.getCreatedby(),
                externalprojectxteam.getLastupdate(),
                externalprojectxteam.getUpdateby());
    }

    private Externalprojectxteam convertToExternalprojectxteamEntity(ExternalProjectTeamDTO externalProjectTeamDTO) {

        Integer agentid = externalProjectTeamDTO.getAgentid();
        Integer teamid = externalProjectTeamDTO.getTeamid();
        if (agentid == null || teamid == null) {
            return null;
        }
        Agentconfig agentconfig = this.agentconfigService.getAgentById(agentid);
        if (agentconfig == null) {
            return null;
        }
        // todo how to check external Team entity existence?

        Externalprojectxteam externalprojectxteam = new Externalprojectxteam();

        externalprojectxteam.setAgentConfig(agentconfig);
        externalprojectxteam.setAgentid(agentid);
        // todo maybe assign external Team entity?
        externalprojectxteam.setTeamid(teamid);
        externalprojectxteam.setRepoid(externalProjectTeamDTO.getRepoid());
        externalprojectxteam.setIsactive(externalProjectTeamDTO.getIsactive());

        return externalprojectxteam;
    }

    /**
     * Table: agents_x_company
     */

    @GetMapping(value = "/AgentsCompany", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsCompanyListResponse> getAgentsCompanyList() {
        List<Agentsxcompany> agentsCompanyList = this.agentsxcompanyService.getAgentsCompanyList();
        return this.convertToAgentsCompanyListResponseEntity(agentsCompanyList);
    }

    // Get by configid - get AgentsCompany entry specifying its ID
    @GetMapping(value = "/AgentsCompany/config/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsCompanyDTO> getAgentsCompanyById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentsxcompany agentsxcompany = this.agentsxcompanyService.getAgentsCompanyById(id);
        if (agentsxcompany == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AgentsCompanyDTO response = this.convertToAgentsCompanyDto(agentsxcompany);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by agentid - get all AgentsCompany entries which belong to specified
    // agentid
    @GetMapping(value = "/AgentsCompany/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsCompanyListResponse> getAgentsCompanyByAgentId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Agentsxcompany> agentsCompanyList = this.agentsxcompanyService.getAgentsCompanyByAgentId(id);
        return this.convertToAgentsCompanyListResponseEntity(agentsCompanyList);
    }

    // Get by companyid - get all AgentsCompany entries which belong to specified
    // companyid
    @GetMapping(value = "/AgentsCompany/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsCompanyListResponse> getAgentsCompanyByCompanyId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // todo check if external Company entity with specified id exists

        List<Agentsxcompany> agentsCompanyList = this.agentsxcompanyService.getAgentsCompanyByCompanyId(id);
        return this.convertToAgentsCompanyListResponseEntity(agentsCompanyList);
    }

    @PostMapping(value = "/AgentsCompany", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsCompanyDTO> postAgentsCompany(@RequestBody AgentsCompanyDTO agentsCompanyDTO) {
        if (agentsCompanyDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentsxcompany agentsxcompany = this.convertToAgentsxcompanyEntity(agentsCompanyDTO);
        if (agentsxcompany == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Agentsxcompany response = this.agentsxcompanyService.postAgentsCompany(agentsxcompany);
            return new ResponseEntity<>(this.convertToAgentsCompanyDto(response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/AgentsCompany/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsCompanyDTO> putAgentsCompany(@PathVariable Integer id,
            @RequestBody AgentsCompanyDTO agentsCompanyDTO) {
        if (id == null || agentsCompanyDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentsxcompany agentsxcompany = this.convertToAgentsxcompanyEntity(agentsCompanyDTO);
        if (agentsxcompany == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Agentsxcompany response = this.agentsxcompanyService.putAgentsCompany(id, agentsxcompany);
            return new ResponseEntity<>(
                    this.convertToAgentsCompanyDto(response),
                    response.getConfigid().equals(agentsCompanyDTO.getConfigid())
                            ? HttpStatus.OK
                            : HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/AgentsCompany/config/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsCompanyDTO> deleteAgentsCompanyById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentsxcompany deletedAgentsCompany = this.agentsxcompanyService.deleteAgentsCompanyById(id);
        return deletedAgentsCompany == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToAgentsCompanyDto(deletedAgentsCompany), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentsCompany/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsCompanyListResponse> deleteAgentsCompanyByAgentId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentsxcompany> deletedAgentsCompanyList = this.agentsxcompanyService.deleteAgentsCompanyByAgentId(id);
        return this.convertToAgentsCompanyListResponseEntity(deletedAgentsCompanyList);
    }

    @DeleteMapping(value = "/AgentsCompany/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsCompanyListResponse> deleteAgentsCompanyByCompanyId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // todo check if external Team entity with specified id exists

        List<Agentsxcompany> deletedAgentsCompanyList = this.agentsxcompanyService.deleteAgentsCompanyByCompanyId(id);
        return this.convertToAgentsCompanyListResponseEntity(deletedAgentsCompanyList);
    }

    private ResponseEntity<AgentsCompanyListResponse> convertToAgentsCompanyListResponseEntity(
            List<Agentsxcompany> agentsCompanyList) {
        if (agentsCompanyList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AgentsCompanyListResponse responseList = new AgentsCompanyListResponse();
        for (Agentsxcompany agentsxcompany : agentsCompanyList) {
            responseList.add(this.convertToAgentsCompanyDto(agentsxcompany));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    private AgentsCompanyDTO convertToAgentsCompanyDto(Agentsxcompany agentsxcompany) {
        return new AgentsCompanyDTO(
                agentsxcompany.getConfigid(),
                agentsxcompany.getAgentid(),
                agentsxcompany.getCompanyid(),
                agentsxcompany.getKey(),
                agentsxcompany.getToken(),
                agentsxcompany.getIsactive(),
                agentsxcompany.getCreationdate(),
                agentsxcompany.getCreatedby(),
                agentsxcompany.getLastupdate(),
                agentsxcompany.getUpdateby());
    }

    private Agentsxcompany convertToAgentsxcompanyEntity(AgentsCompanyDTO agentsCompanyDTO) {

        Integer agentid = agentsCompanyDTO.getAgentid();
        Integer companyid = agentsCompanyDTO.getCompanyid();
        if (agentid == null || companyid == null) {
            return null;
        }
        Agentconfig agentconfig = this.agentconfigService.getAgentById(agentid);
        if (agentconfig == null) {
            return null;
        }
        // todo how to check external Company entity existence?

        Agentsxcompany agentsxcompany = new Agentsxcompany();

        agentsxcompany.setAgentConfig(agentconfig);
        agentsxcompany.setAgentid(agentid);
        // todo maybe assign external Team entity?
        agentsxcompany.setCompanyid(companyid);
        agentsxcompany.setKey(agentsCompanyDTO.getKey());
        agentsxcompany.setToken(agentsCompanyDTO.getToken());
        agentsxcompany.setIsactive(agentsCompanyDTO.getIsactive());

        return agentsxcompany;
    }

    /**
     * Table: repos_x_project
     */

    @GetMapping(value = "/ReposProject", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposProjectListResponse> getReposProjectList() {
        List<Reposxproject> reposProjectList = this.reposxprojectService.getReposProjectList();
        return this.convertToReposProjectListResponseEntity(reposProjectList);
    }

    // Get by configid - get ReposProject entry specifying its ID
    @GetMapping(value = "/ReposProject/config/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposProjectDTO> getReposProjectById(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Reposxproject reposxproject = this.reposxprojectService.getReposProjectById(id);
        if (reposxproject == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ReposProjectDTO response = this.convertToReposProjectDto(reposxproject);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by agentid - get all ReposProject entries which belong to specified
    // agentid
    @GetMapping(value = "/ReposProject/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposProjectListResponse> getReposProjectByAgentId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Reposxproject> reposProjectList = this.reposxprojectService.getReposProjectByAgentId(id);
        return this.convertToReposProjectListResponseEntity(reposProjectList);
    }

    // Get by projectid - get all ReposProject entries which belong to specified
    // projectid
    @GetMapping(value = "/ReposProject/project/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposProjectListResponse> getReposProjectByProjectId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // todo check if external Project entity with specified id exists

        List<Reposxproject> reposProjectList = this.reposxprojectService.getReposProjectByProjectId(id);
        return this.convertToReposProjectListResponseEntity(reposProjectList);
    }

    @PostMapping(value = "/ReposProject", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposProjectDTO> postReposProject(@RequestBody ReposProjectDTO reposProjectDTO) {
        if (reposProjectDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Reposxproject reposxproject = this.convertToReposxprojectEntity(reposProjectDTO);
        if (reposxproject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Reposxproject response = this.reposxprojectService.postReposProject(reposxproject);
            return new ResponseEntity<>(this.convertToReposProjectDto(response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/ReposProject/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposProjectDTO> putReposProject(@PathVariable Integer id,
            @RequestBody ReposProjectDTO reposProjectDTO) {
        if (id == null || reposProjectDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Reposxproject reposxproject = this.convertToReposxprojectEntity(reposProjectDTO);
        if (reposxproject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Reposxproject response = this.reposxprojectService.putReposProject(id, reposxproject);
            return new ResponseEntity<>(
                    this.convertToReposProjectDto(response),
                    response.getConfigid().equals(reposProjectDTO.getConfigid())
                            ? HttpStatus.OK
                            : HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/ReposProject/config/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposProjectDTO> deleteReposProjectById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Reposxproject deletedReposProject = this.reposxprojectService.deleteReposProjectById(id);
        return deletedReposProject == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToReposProjectDto(deletedReposProject), HttpStatus.OK);
    }

    @DeleteMapping(value = "/ReposProject/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposProjectListResponse> deleteReposProjectByAgentId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Reposxproject> deletedReposProjectList = this.reposxprojectService.deleteReposProjectByAgentId(id);
        return this.convertToReposProjectListResponseEntity(deletedReposProjectList);
    }

    @DeleteMapping(value = "/ReposProject/project/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReposProjectListResponse> deleteReposProjectByProjectId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // todo check if external Project entity with specified id exists

        List<Reposxproject> deletedReposProjectList = this.reposxprojectService.deleteReposProjectByProjectId(id);
        return this.convertToReposProjectListResponseEntity(deletedReposProjectList);
    }

    private ResponseEntity<ReposProjectListResponse> convertToReposProjectListResponseEntity(
            List<Reposxproject> reposProjectList) {
        if (reposProjectList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ReposProjectListResponse responseList = new ReposProjectListResponse();
        for (Reposxproject reposxproject : reposProjectList) {
            responseList.add(this.convertToReposProjectDto(reposxproject));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    private ReposProjectDTO convertToReposProjectDto(Reposxproject reposxproject) {
        return new ReposProjectDTO(
                reposxproject.getConfigid(),
                reposxproject.getAgentid(),
                reposxproject.getProjectid(),
                reposxproject.getRepoid(),
                reposxproject.getIsactive(),
                reposxproject.getCreationdate(),
                reposxproject.getCreatedby(),
                reposxproject.getLastupdate(),
                reposxproject.getUpdateby());
    }

    private Reposxproject convertToReposxprojectEntity(ReposProjectDTO reposProjectDTO) {

        Integer agentid = reposProjectDTO.getAgentid();
        Integer projectid = reposProjectDTO.getProjectid();
        if (agentid == null || projectid == null) {
            return null;
        }
        Agentconfig agentconfig = this.agentconfigService.getAgentById(agentid);
        if (agentconfig == null) {
            return null;
        }
        // todo how to check external Project entity existence?

        Reposxproject reposxproject = new Reposxproject();

        reposxproject.setAgentid(agentid);
        // todo maybe assign external Project entity?
        reposxproject.setProjectid(projectid);
        reposxproject.setRepoid(reposProjectDTO.getRepoid());
        reposxproject.setIsactive(reposProjectDTO.getIsactive());

        return reposxproject;
    }

    /**
     * Table: agents_x_project
     */

    @GetMapping(value = "/AgentsProject", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectListResponse> getAgentsProjectList() {
        List<Agentsxproject> agentsProjectList = this.agentsxprojectService.getAgentsProjectList();
        return this.convertToAgentsProjectListResponseEntity(agentsProjectList);
    }

    // Get by configid - get AgentsProject entry specifying its ID
    @GetMapping(value = "/AgentsProject/config/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectDTO> getAgentsProjectById(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentsxproject agentsxproject = this.agentsxprojectService.getAgentsProjectById(id);
        if (agentsxproject == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AgentsProjectDTO response = this.convertToAgentsProjectDto(agentsxproject);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by agentid - get all AgentsProject entries which belong to specified
    // agentid
    @GetMapping(value = "/AgentsProject/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectListResponse> getAgentsProjectByAgentId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentsxproject> agentsProjectList = this.agentsxprojectService.getAgentsProjectByAgentId(id);
        return this.convertToAgentsProjectListResponseEntity(agentsProjectList);
    }

    // Get by projectid - get all AgentsProject entries which belong to specified
    // projectid
    @GetMapping(value = "/AgentsProject/project/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectListResponse> getAgentsProjectByProjectId(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // todo check if external Project entity with specified id exists

        List<Agentsxproject> agentsProjectList = this.agentsxprojectService.getAgentsProjectByProjectId(id);
        return this.convertToAgentsProjectListResponseEntity(agentsProjectList);
    }

    // Get by agentid and projectid - get AgentsProject entry specifying its agentid
    // and projectid
    @GetMapping(value = "/AgentsProject/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectDTO> getAgentsProjectByAgentIdAndProjectId(@RequestParam Integer agentid,
            @RequestParam Integer projectid) {

        if (agentid == null || projectid == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Agentsxproject agentsxproject = this.agentsxprojectService.getAgentsProjectByAgentIdAndProjectId(agentid,
                projectid);
        if (agentsxproject == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AgentsProjectDTO response = this.convertToAgentsProjectDto(agentsxproject);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/AgentsProject", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectDTO> postAgentsProject(@RequestBody AgentsProjectDTO agentsProjectDTO) {
        if (agentsProjectDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentsxproject agentsxproject = this.convertToAgentsxprojectEntity(agentsProjectDTO);
        if (agentsxproject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Agentsxproject response = this.agentsxprojectService.postAgentsProject(agentsxproject);
            return new ResponseEntity<>(this.convertToAgentsProjectDto(response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/AgentsProject/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectDTO> putAgentsProject(@PathVariable Integer id,
            @RequestBody AgentsProjectDTO agentsProjectDTO) {
        if (id == null || agentsProjectDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentsxproject agentsxproject = this.convertToAgentsxprojectEntity(agentsProjectDTO);
        if (agentsxproject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Agentsxproject response = this.agentsxprojectService.putAgentsProject(id, agentsxproject);
            return new ResponseEntity<>(
                    this.convertToAgentsProjectDto(response),
                    response.getConfigid().equals(agentsProjectDTO.getConfigid())
                            ? HttpStatus.OK
                            : HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/AgentsProject/config/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectDTO> deleteAgentsProjectById(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Agentsxproject deletedAgentsProject = this.agentsxprojectService.deleteAgentsProjectById(id);
        return deletedAgentsProject == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(this.convertToAgentsProjectDto(deletedAgentsProject), HttpStatus.OK);
    }

    @DeleteMapping(value = "/AgentsProject/agent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectListResponse> deleteAgentsProjectByAgentId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.agentconfigService.getAgentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Agentsxproject> deletedAgentsProjectList = this.agentsxprojectService.deleteAgentsProjectByAgentId(id);
        return this.convertToAgentsProjectListResponseEntity(deletedAgentsProjectList);
    }

    @DeleteMapping(value = "/AgentsProject/project/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentsProjectListResponse> deleteAgentsProjectByProjectId(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // todo check if external Project entity with specified id exists

        List<Agentsxproject> deletedAgentsProjectList = this.agentsxprojectService.deleteAgentsProjectByProjectId(id);
        return this.convertToAgentsProjectListResponseEntity(deletedAgentsProjectList);
    }

    private ResponseEntity<AgentsProjectListResponse> convertToAgentsProjectListResponseEntity(
            List<Agentsxproject> agentsProjectList) {
        if (agentsProjectList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AgentsProjectListResponse responseList = new AgentsProjectListResponse();
        for (Agentsxproject agentsxproject : agentsProjectList) {
            responseList.add(this.convertToAgentsProjectDto(agentsxproject));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    private AgentsProjectDTO convertToAgentsProjectDto(Agentsxproject agentsxproject) {
        return new AgentsProjectDTO(
                agentsxproject.getConfigid(),
                agentsxproject.getAgentid(),
                agentsxproject.getProjectid(),
                agentsxproject.getKey(),
                agentsxproject.getToken(),
                agentsxproject.getIsactive(),
                agentsxproject.getCreationdate(),
                agentsxproject.getCreatedby(),
                agentsxproject.getLastupdate(),
                agentsxproject.getUpdateby());
    }

    private Agentsxproject convertToAgentsxprojectEntity(AgentsProjectDTO agentsProjectDTO) {

        Integer agentid = agentsProjectDTO.getAgentid();
        Integer projectid = agentsProjectDTO.getProjectid();
        if (agentid == null || projectid == null) {
            return null;
        }
        Agentconfig agentconfig = this.agentconfigService.getAgentById(agentid);
        if (agentconfig == null) {
            return null;
        }
        // todo how to check external Project entity existence?

        Agentsxproject agentsxproject = new Agentsxproject();

        agentsxproject.setAgentConfig(agentconfig);
        agentsxproject.setAgentid(agentid);
        // todo maybe assign external Project entity?
        agentsxproject.setProjectid(projectid);
        agentsxproject.setKey(agentsProjectDTO.getKey());
        agentsxproject.setToken(agentsProjectDTO.getToken());
        agentsxproject.setIsactive(agentsProjectDTO.getIsactive());

        return agentsxproject;
    }
}
