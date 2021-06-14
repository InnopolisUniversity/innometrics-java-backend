package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentconfigService {
    @Autowired
    AgentconfigRepository agentconfigRepository;

    @Autowired
    AgentconfigmethodsRepository agentconfigmethodsRepository;

    private AgentResponse setAllFields(Agentconfig agent){
        AgentResponse response = new AgentResponse();
        response.setAgentid(agent.getAgentid());
        response.setAgentname(agent.getAgentname());
        response.setDescription(agent.getDescription());
        response.setIsconnected(agent.getIsactive());
        response.setCreationdate(agent.getCreationdate());
        response.setCreatedby(agent.getCreatedby());
        response.setLastupdate(agent.getLastupdate());
        response.setUpdateby(agent.getUpdateby());
        response.setSourcetype(agent.getSourcetype());
        response.setDbschemasource(agent.getDbschemasource());
        response.setRepoidfield(agent.getRepoidfield());
        response.setOathuri(agent.getOauthuri());
        response.setAuthenticationmethod(agent.getAuthenticationmethod());
        response.setAccesstokenendpoint(agent.getAccesstokenendpoint());
        response.setAuthorizationbaseurl(agent.getAuthorizationbaseurl());
        response.setRequesttokenendpoint(agent.getRequesttokenendpoint());
        response.setApikey(agent.getApikey());
        response.setApisecret(agent.getApisecret());
        return response;
    }

    public AgentListResponse getAgentList(Integer projectId) {
        List<IAgentStatus> result = agentconfigRepository.getAgentList(projectId);

        AgentListResponse response = new AgentListResponse();
        for (IAgentStatus a : result) {
            AgentResponse tmp = new AgentResponse();

            tmp.setAgentid(Integer.valueOf(a.getAgentid()));
            tmp.setAgentname(a.getAgentname());
            tmp.setDescription(a.getDescription());
            tmp.setIsconnected(a.getIsconnected());
            response.getAgentList().add(tmp);
        }

        return response;
    }

    public AgentResponse getAgent(Integer agentId){
        Agentconfig agent = agentconfigRepository.findById(agentId).orElseThrow(()->new RuntimeException(String.valueOf(agentId)));
        return this.setAllFields(agent);
    }

    public AgentResponse putAgent(Integer agentId, Agentconfig config){
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
            return this.setAllFields(agent);
        }).orElseGet(()->{
            config.setAgentid(agentId);
            agentconfigRepository.save(config);
            return this.setAllFields(config);
        });
    }

    public AgentResponse postAgent(Agentconfig config){
        agentconfigRepository.save(config);
        return this.setAllFields(config);
    }

    public AgentResponse deleteAgent(Integer agentId){
        Agentconfig agent = agentconfigRepository.findByAgentid(agentId);
        agentconfigRepository.deleteById(agentId);
        return this.setAllFields(agent);
    }

    public AgentConfigResponse getAgentConfig(Integer AgentId, String CallType) {
        List<Agentconfigmethods> result = agentconfigmethodsRepository.findByAgentid(AgentId);

        AgentConfigResponse response = new AgentConfigResponse();
        for (Agentconfigmethods a : result) {
            if (a.getOperation().equals(CallType) || CallType == null) {
                MethodConfigDTO tmp = new MethodConfigDTO();
                tmp.setMethodid(a.getMethodid());
                tmp.setOperation(a.getOperation());
                tmp.setDescription(a.getDescription());
                for (Agentconfigdetails d : a.getParams()) {
                    ParamsConfigDTO tmp2 = new ParamsConfigDTO();
                    tmp2.setParamname(d.getParamname());
                    tmp2.setParamtype(d.getParamtype());
                    tmp.getParameters().add(tmp2);
                }

                response.getMethodsConfig().add(tmp);
            }
        }

        return response;
    }
    //todo other tables

}
