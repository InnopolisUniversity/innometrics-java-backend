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

    public AgentListResponse getAgentList() {
        List<Agentconfig> result = agentconfigRepository.findAll();

        AgentListResponse response = new AgentListResponse();
        for (Agentconfig a : result) {
            AgentResponse tmp = new AgentResponse();

            tmp.setAgentid(a.getAgentid());
            tmp.setAgentname(a.getAgentname());
            tmp.setDescription(a.getDescription());
            response.getAgentList().add(tmp);
        }

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
}
