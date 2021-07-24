package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentconfigService {
    @Autowired
    AgentconfigRepository agentconfigRepository;
    @Autowired
    AgentconfigmethodsRepository agentconfigmethodsRepository;

    @Autowired
    AgentdataconfigService agentdataconfigService;
    @Autowired
    AgentconfigmethodsService agentconfigmethodsService;
    @Autowired
    ReposxprojectService reposxprojectService;
    @Autowired
    AgentsxprojectService agentsxprojectService;
    @Autowired
    ExternalprojectxteamService externalprojectxteamService;
    @Autowired
    AgentsxcompanyService agentsxcompanyService;

    public AgentConfigResponse getAgentConfig(Integer agentId, String CallType) {
        List<Agentconfigmethods> result = this.agentconfigmethodsRepository.findByAgentid(agentId);
        if (result == null) {
            return null;
        }

        AgentConfigResponse response = new AgentConfigResponse();

        for (Agentconfigmethods agentConfigMethod : result) {
            if (agentConfigMethod.getOperation().equals(CallType) || CallType == null) {
                List<ParamsConfigDTO> parameters = new ArrayList<>();
                for (Agentconfigdetails agentconfigdetails : agentConfigMethod.getParams()) {
                    ParamsConfigDTO paramsConfigDTO = new ParamsConfigDTO();

                    paramsConfigDTO.setParamname(agentconfigdetails.getParamname());
                    paramsConfigDTO.setParamtype(agentconfigdetails.getParamtype());

                    parameters.add(paramsConfigDTO);
                }
                response.addMethodConfig(
                        new MethodConfigDTO(
                                agentConfigMethod.getMethodid(),
                                agentConfigMethod.getOperation(),
                                agentConfigMethod.getDescription(),
                                parameters
                        )
                );
            }
        }

        return response;
    }

    public AgentListResponse getAgentList(Integer projectId) {
        AgentListResponse agentListResponse = new AgentListResponse();

        for (IAgentStatus agentStatus : this.agentconfigRepository.getAgentList(projectId)) {
            agentListResponse.add(
                    new AgentResponse(
                            Integer.valueOf(agentStatus.getAgentid()),
                            agentStatus.getAgentname(),
                            agentStatus.getDescription(),
                            agentStatus.getIsconnected()
                    )
            );
        }

        return agentListResponse;
    }

    public Agentconfig getAgentById(Integer agentId) {
        return this.agentconfigRepository.findByAgentid(agentId);
    }

    public Agentconfig postAgent(Agentconfig agentconfig) {
        return this.agentconfigRepository.save(agentconfig);
    }

    public Agentconfig putAgent(Integer agentId, Agentconfig agentconfig) {
        return this.agentconfigRepository.findById(agentId).map(agent -> {
            agent.setAgentname(agentconfig.getAgentname());
            agent.setDescription(agentconfig.getDescription());
            agent.setIsactive(agentconfig.getIsactive());
            agent.setSourcetype(agentconfig.getSourcetype());
            agent.setDbschemasource(agentconfig.getDbschemasource());
            agent.setRepoidfield(agentconfig.getRepoidfield());
            agent.setOauthuri(agentconfig.getOauthuri());
            agent.setAuthenticationmethod(agentconfig.getAuthenticationmethod());
            agent.setAccesstokenendpoint(agentconfig.getAccesstokenendpoint());
            agent.setAuthorizationbaseurl(agentconfig.getAuthorizationbaseurl());
            agent.setRequesttokenendpoint(agentconfig.getRequesttokenendpoint());
            agent.setApikey(agentconfig.getApikey());
            agent.setApisecret(agentconfig.getApisecret());

            return this.agentconfigRepository.save(agent);
        }).orElseGet(() -> this.agentconfigRepository.save(agentconfig));
    }

    public Agentconfig deleteAgentById(Integer agentId) {
        // Check agent existence
        Agentconfig agentconfig = this.agentconfigRepository.findByAgentid(agentId);
        if (agentconfig == null) {
            return null;
        }

        // Delete methods which belong to this agent
        this.agentconfigmethodsService.deleteMethodsByAgentId(agentId);
        // Delete data which belong to this agent
        this.agentdataconfigService.deleteDataByAgentId(agentId);
        // Delete ReposProject entries which belong to this agent
        this.reposxprojectService.deleteReposProjectByAgentId(agentId);
        // Delete AgentsProject entries which belong to this agent
        this.agentsxprojectService.deleteAgentsProjectByAgentId(agentId);
        // Delete ExternalProjectTeam entries which belong to this agent
        this.externalprojectxteamService.deleteExternalProjectTeamByAgentId(agentId);
        // Delete AgentsCompany entries which belong to this agent
        this.agentsxcompanyService.deleteAgentsCompanyByAgentId(agentId);

        // Delete agent itself
        this.agentconfigRepository.deleteById(agentId);
        return agentconfig;
    }
}
