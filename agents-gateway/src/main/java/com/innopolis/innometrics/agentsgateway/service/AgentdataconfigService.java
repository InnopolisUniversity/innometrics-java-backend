package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentdataconfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentdataconfigService {
    @Autowired
    AgentdataconfigRepository agentdataconfigRepository;

    // todo write methods
/*
    public AgentListResponse getDataConfigList() {
        List<IAgentStatus> result = agentdataconfigRepository.getAgentList();

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

    public AgentResponse getDataConfig(Integer agentId) {
        //Agentconfig agent = agentconfigRepository.findById(agentId).orElseThrow(() -> new RuntimeException(String.valueOf(agentId)));
        Agentconfig agent = agentdataconfigRepository.findByAgentid(agentId);
        return agent == null ? null : this.getResponseFromAgentConfig(agent);
    }

    public AgentResponse postDataConfig(Agentconfig config) {
        agentdataconfigRepository.save(config);
        return this.getResponseFromAgentConfig(config);
    }

    public AgentResponse putDataConfig(Integer agentId, Agentconfig config) {
        return agentdataconfigRepository.findById(agentId).map(agent -> {
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

            agentdataconfigRepository.save(agent);
            return this.getResponseFromAgentConfig(agent);
        }).orElseGet(() -> {
            agentdataconfigRepository.save(config);
            return this.getResponseFromAgentConfig(config);
        });
    }

    public AgentResponse deleteDataConfig(Integer agentId) {
        Agentconfig agent = agentdataconfigRepository.findByAgentid(agentId);
        if (agent == null) {
            return null;
        }
        agentdataconfigRepository.deleteById(agentId);
        return this.getResponseFromAgentConfig(agent);
    }
*/
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
