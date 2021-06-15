package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigdetailsRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentconfigdetailsService {
    @Autowired
    AgentconfigdetailsRepository agentconfigdetailsRepository;
    // todo write methods
/*
    public AgentListResponse getAgentConfigDetailsList() {
        List<Agentconfigdetails> result = agentconfigdetailsRepository.findAll();

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


    public AgentResponse getConfigDetails(Integer agentId) {
        //Agentconfig agent = agentconfigRepository.findById(agentId).orElseThrow(() -> new RuntimeException(String.valueOf(agentId)));
        Agentconfig agent = agentconfigdetailsRepository.findByAgentid(agentId);
        return agent == null ? null : this.getResponseFromAgentConfig(agent);
    }

    public AgentResponse postConfigDetails(Agentconfig config) {
        agentconfigdetailsRepository.save(config);
        return this.getResponseFromAgentConfig(config);
    }

    public AgentResponse putConfigDetails(Integer agentId, Agentconfig config) {
        return agentconfigdetailsRepository.findById(agentId).map(agent -> {
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

            agentconfigdetailsRepository.save(agent);
            return this.getResponseFromAgentConfig(agent);
        }).orElseGet(() -> {
            agentconfigdetailsRepository.save(config);
            return this.getResponseFromAgentConfig(config);
        });
    }

    public AgentResponse deleteConfigDetails(Integer agentId) {
        Agentconfig agent = agentconfigdetailsRepository.findByAgentid(agentId);
        if (agent == null) {
            return null;
        }
        agentconfigdetailsRepository.deleteById(agentId);
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
