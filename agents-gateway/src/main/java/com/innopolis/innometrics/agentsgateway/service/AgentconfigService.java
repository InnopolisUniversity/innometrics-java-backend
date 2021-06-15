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

    public AgentConfigResponse getAgentConfig(Integer agentId, String CallType) {
        List<Agentconfigmethods> result = agentconfigmethodsRepository.findByAgentid(agentId);
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
                    //methodConfigDTO.getParameters().add(paramsConfigDTO);
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
        List<IAgentStatus> result = agentconfigRepository.getAgentList(projectId);

        AgentListResponse agentListResponse = new AgentListResponse();
        for (IAgentStatus element : result) {
            AgentResponse agentResponse = new AgentResponse(
                    Integer.valueOf(element.getAgentid()),
                    element.getAgentname(),
                    element.getDescription(),
                    element.getIsconnected()
            );

            agentListResponse.add(agentResponse);
        }

        return agentListResponse;
    }

    public AgentResponse getAgent(Integer agentId) {
        //Agentconfig agent = agentconfigRepository.findById(agentId).orElseThrow(() -> new RuntimeException(String.valueOf(agentId)));
        Agentconfig agent = agentconfigRepository.findByAgentid(agentId);
        return agent == null ? null : this.getResponseFromAgentConfig(agent);
    }

    public AgentResponse postAgent(Agentconfig config) {
        agentconfigRepository.save(config);
        return this.getResponseFromAgentConfig(config);
    }

    public AgentResponse putAgent(Integer agentId, Agentconfig config) {
        return agentconfigRepository.findById(agentId).map(agent -> {
            agent.setAgentname(config.getAgentname());
            agent.setDescription(config.getDescription());
            agent.setIsactive(config.getIsactive());
            agent.setCreationdate(config.getCreationdate());
            agent.setCreatedby(config.getCreatedby());
            agent.setLastupdate(config.getLastupdate());
            agent.setUpdateby(config.getUpdateby());
            agent.setSourcetype(config.getSourcetype());
            agent.setDbschemasource(config.getDbschemasource());
            agent.setRepoidfield(config.getRepoidfield());
            agent.setOauthuri(config.getOauthuri());
            agent.setAuthenticationmethod(config.getAuthenticationmethod());
            agent.setAccesstokenendpoint(config.getAccesstokenendpoint());
            agent.setAuthorizationbaseurl(config.getAuthorizationbaseurl());
            agent.setRequesttokenendpoint(config.getRequesttokenendpoint());
            agent.setApikey(config.getApikey());
            agent.setApisecret(config.getApisecret());

            agentconfigRepository.save(agent);
            return this.getResponseFromAgentConfig(agent);
        }).orElseGet(() -> {
            agentconfigRepository.save(config);
            return this.getResponseFromAgentConfig(config);
        });
    }

    public AgentResponse deleteAgent(Integer agentId) {
        Agentconfig agent = agentconfigRepository.findByAgentid(agentId);
        if (agent == null) {
            return null;
        }
        agentconfigRepository.deleteById(agentId);
        return this.getResponseFromAgentConfig(agent);
    }

    private AgentResponse getResponseFromAgentConfig(Agentconfig agentConfig) {
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
}
