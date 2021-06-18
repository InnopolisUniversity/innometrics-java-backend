package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentconfigmethodsService {
    @Autowired
    AgentconfigmethodsRepository agentconfigmethodsRepository;

    public List<Agentconfigmethods> getMethodsList() {
        return agentconfigmethodsRepository.findAll();
    }

    public List<Agentconfigmethods> getMethodsByAgentId(Integer agentId) {
        return agentconfigmethodsRepository.findByAgentid(agentId);
    }

    public Agentconfigmethods getMethodByMethodId(Integer methodId) {
        return agentconfigmethodsRepository.findById(methodId).orElse(null);
    }

    public Agentconfigmethods getMethodsByAgentidAndOperation(Integer agentId, String operation) {
        return agentconfigmethodsRepository.findByAgentidAndOperation(agentId, operation);
    }

    public Agentconfigmethods postMethod(Agentconfigmethods agentconfigmethods) {
        //todo set creation date manually?
        return agentconfigmethodsRepository.save(agentconfigmethods);
    }

    public Agentconfigmethods putMethod(Integer methodId, Agentconfigmethods method) {
        return agentconfigmethodsRepository.findById(methodId).map(agentMethod -> {
            agentMethod.setAgentid(method.getAgentid());
            agentMethod.setAgentConfig(method.getAgentConfig());
            agentMethod.setDescription(method.getDescription());
            agentMethod.setEndpoint(method.getEndpoint());
            agentMethod.setIsactive(method.getIsactive());
            agentMethod.setOperation(method.getOperation());
            agentMethod.setRequesttype(method.getRequesttype());
            //todo set last updated date manually?

            agentconfigmethodsRepository.save(agentMethod);
            return agentMethod;
        }).orElseGet(() -> {
            agentconfigmethodsRepository.save(method);
            return method;
        });
    }

    public List<Agentconfigmethods> deleteMethodsByAgentId(Integer agentId) {
        List<Agentconfigmethods> methods = agentconfigmethodsRepository.findByAgentid(agentId);
        if (methods == null || methods.isEmpty()) {
            return null;
        }
        List<Agentconfigmethods> deletedMethods = new ArrayList<>(methods.size());
        for (Agentconfigmethods method : methods) {
            Agentconfigmethods deletedMethod = this.deleteMethodByMethodId(method.getMethodid());
            if (deletedMethod != null) {
                deletedMethods.add(deletedMethod);
            }
        }

        return deletedMethods;
    }

    public Agentconfigmethods deleteMethodByMethodId(Integer methodId) {
        Optional<Agentconfigmethods> methods = agentconfigmethodsRepository.findById(methodId);
        if (!methods.isPresent()) {
            return null;
        }
        agentconfigmethodsRepository.deleteById(methodId);
        return methods.get();
    }
}
