package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentconfigmethodsService {
    @Autowired
    AgentconfigmethodsRepository agentconfigmethodsRepository;

    public List<MethodConfigDTO> getMethodsList() {
        List<Agentconfigmethods> result = agentconfigmethodsRepository.findAll();
        return this.getMethodConfigListFromAgentconfigmethods(result);
    }

    public List<MethodConfigDTO> getMethod(Integer methodId) {
        List<Agentconfigmethods> result = agentconfigmethodsRepository.findByAgentid(methodId);
        if (result == null) {
            return null;
        }
        return this.getMethodConfigListFromAgentconfigmethods(result);
    }

    public MethodConfigDTO postMethod(Agentconfigmethods method) {
        agentconfigmethodsRepository.save(method);
        return this.getMethodConfigFromAgentconfigmethods(method);
    }

    public MethodConfigDTO putMethod(Integer methodId, Agentconfigmethods method) {
        return agentconfigmethodsRepository.findById(methodId).map(agentMethod -> {
            agentMethod.setAgentConfig(method.getAgentConfig());
            agentMethod.setCreatedby(method.getCreatedby());
            agentMethod.setCreationdate(method.getCreationdate());
            agentMethod.setDescription(method.getDescription());
            agentMethod.setEndpoint(method.getEndpoint());
            agentMethod.setIsactive(method.getIsactive());
            agentMethod.setLastupdate(method.getLastupdate());
            agentMethod.setMethodid(method.getMethodid());
            agentMethod.setOperation(method.getOperation());
            agentMethod.setParams(method.getParams());
            agentMethod.setRequesttype(method.getRequesttype());
            agentMethod.setResponseParams(method.getResponseParams());
            agentMethod.setUpdateby(method.getUpdateby());

            agentconfigmethodsRepository.save(agentMethod);
            return this.getMethodConfigFromAgentconfigmethods(agentMethod);
        }).orElseGet(() -> {
            agentconfigmethodsRepository.save(method);
            return this.getMethodConfigFromAgentconfigmethods(method);
        });
    }

    public List<MethodConfigDTO> deleteMethod(Integer methodId) {
        List<Agentconfigmethods> methods = agentconfigmethodsRepository.findByAgentid(methodId);
        if (methods == null) {
            return null;
        }
        agentconfigmethodsRepository.deleteById(methodId);
        return this.getMethodConfigListFromAgentconfigmethods(methods);
    }

    private List<MethodConfigDTO> getMethodConfigListFromAgentconfigmethods(List<Agentconfigmethods> agentconfigmethods) {
        List<MethodConfigDTO> methodsList = new ArrayList<>();
        for (Agentconfigmethods method : agentconfigmethods) {
            methodsList.add(this.getMethodConfigFromAgentconfigmethods(method));
        }
        return methodsList;
    }

    //todo add .setParameters()
    private MethodConfigDTO getMethodConfigFromAgentconfigmethods(Agentconfigmethods agentconfigmethod) {
        MethodConfigDTO methodConfig = new MethodConfigDTO();
        methodConfig.setMethodid(agentconfigmethod.getAgentid());
        methodConfig.setDescription(agentconfigmethod.getDescription());
        methodConfig.setOperation(agentconfigmethod.getOperation());
        //methodConfig.setParameters(agentconfigmethod.getParams());

        return methodConfig;
    }
}
