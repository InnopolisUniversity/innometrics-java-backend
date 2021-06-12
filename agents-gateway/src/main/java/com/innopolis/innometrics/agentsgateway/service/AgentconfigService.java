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
        AgentResponse response = new AgentResponse();
        response.setAgentid(agent.getAgentid());
        response.setAgentname(agent.getAgentname());
        response.setDescription(agent.getDescription());
        response.setIsconnected(agent.getIsactive());
        return response;
    }

    public AgentResponse putAgent(Integer agentId, Agentconfig config){
        return agentconfigRepository.findById(agentId).map(agent -> {
            agent.setAgentname(config.getAgentname());
            agent.setDescription(config.getDescription());
            agent.setIsactive(config.getIsactive());
            AgentResponse response = new AgentResponse();
            agentconfigRepository.save(agent);
            response.setAgentid(agent.getAgentid());
            response.setAgentname(agent.getAgentname());
            response.setDescription(agent.getDescription());
            response.setIsconnected(agent.getIsactive());
            return response;
        }).orElseGet(()->{
            config.setAgentid(agentId);
            agentconfigRepository.save(config);
            AgentResponse response = new AgentResponse();
            response.setAgentid(config.getAgentid());
            response.setAgentname(config.getAgentname());
            response.setDescription(config.getDescription());
            response.setIsconnected(config.getIsactive());
            return response;
        });
    }

    public AgentResponse postAgent(Agentconfig config){
        agentconfigRepository.save(config);//todo check for autoincrement
        AgentResponse response = new AgentResponse();
        response.setAgentid(config.getAgentid());
        response.setAgentname(config.getAgentname());
        response.setDescription(config.getDescription());
        response.setIsconnected(config.getIsactive());
        return response;
    }

    public AgentResponse deleteAgent(Integer agentId){
        Agentconfig agent = agentconfigRepository.findByAgentid(agentId);
        agentconfigRepository.deleteById(agentId);
        AgentResponse response = new AgentResponse();
        response.setAgentid(agent.getAgentid());
        response.setAgentname(agent.getAgentname());
        response.setDescription(agent.getDescription());
        response.setIsconnected(agent.getIsactive());
        return response;
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
    //todo this and other things
    //public AgentConfigResponse postAgentConfig(Integer agentId){}

    //public void deleteAgentConfig(){}
}
