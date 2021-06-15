package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentresponseconfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentresponseconfigService {
    @Autowired
    AgentresponseconfigRepository agentresponseconfigRepository;

    // todo write methods
/*
    public AgentListResponse getResponseConfigList() {
        List<IAgentStatus> result = agentresponseconfigRepository.getAgentList(projectId);

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

    public AgentResponse getResponseConfig(Integer agentId) {
        //Agentconfig agent = agentresponseconfigRepository.findById(agentId).orElseThrow(() -> new RuntimeException(String.valueOf(agentId)));
        Agentconfig agent = agentresponseconfigRepository.findByAgentid(agentId);
        return agent == null ? null : this.getResponseFromAgentConfig(agent);
    }

    public AgentResponse postResponseConfig(Agentconfig config) {
        agentresponseconfigRepository.save(config);
        return this.getResponseFromAgentConfig(config);
    }

    public AgentResponse putResponseConfig(Integer agentId, Agentconfig config) {
        return agentresponseconfigRepository.findById(agentId).map(agent -> {
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

            agentresponseconfigRepository.save(agent);
            return this.getResponseFromAgentConfig(agent);
        }).orElseGet(() -> {
            agentresponseconfigRepository.save(config);
            return this.getResponseFromAgentConfig(config);
        });
    }

    public AgentResponse deleteResponseConfig(Integer agentId) {
        Agentconfig agent = agentresponseconfigRepository.findByAgentid(agentId);
        if (agent == null) {
            return null;
        }
        agentresponseconfigRepository.deleteById(agentId);
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

 */
}
