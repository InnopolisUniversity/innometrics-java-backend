package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigmethodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgentconfigmethodsService {
    @Autowired
    AgentconfigmethodsRepository agentconfigmethodsRepository;

    @Autowired
    AgentconfigdetailsService agentconfigdetailsService;
    @Autowired
    AgentresponseconfigService agentresponseconfigService;

    public List<Agentconfigmethods> getMethodsList() {
        return this.agentconfigmethodsRepository.findAll();
    }

    public List<Agentconfigmethods> getMethodsByAgentId(Integer agentId) {
        return this.agentconfigmethodsRepository.findByAgentid(agentId);
    }

    public Agentconfigmethods getMethodById(Integer methodId) {
        return this.agentconfigmethodsRepository.findById(methodId).orElse(null);
    }

    public Agentconfigmethods getMethodsByAgentidAndOperation(Integer agentId, String operation) {
        return this.agentconfigmethodsRepository.findByAgentidAndOperation(agentId, operation);
    }

    public Agentconfigmethods postMethod(Agentconfigmethods agentconfigmethods) {
        Set<Agentconfigdetails> configParameters = new HashSet<>(agentconfigmethods.getParams());

        agentconfigmethods.setParams(new HashSet<Agentconfigdetails>());
        Agentconfigmethods createdEntity = this.agentconfigmethodsRepository.save(agentconfigmethods);

        /*
        for (Agentconfigdetails configParameter : configParameters) {
            configParameter.setAgentconfigmethods(createdEntity);
            this.agentconfigdetailsService.postDetails(configParameter);
        }
         */

        return createdEntity;
    }

    public Agentconfigmethods putMethod(Integer methodId, Agentconfigmethods method) {
        return this.agentconfigmethodsRepository.findById(methodId).map(agentMethod -> {
            agentMethod.setAgentid(method.getAgentid());
            agentMethod.setAgentConfig(method.getAgentConfig());
            agentMethod.setDescription(method.getDescription());
            agentMethod.setEndpoint(method.getEndpoint());
            agentMethod.setIsactive(method.getIsactive());
            agentMethod.setOperation(method.getOperation());
            agentMethod.setRequesttype(method.getRequesttype());

            return this.agentconfigmethodsRepository.save(agentMethod);
        }).orElseGet(() -> this.agentconfigmethodsRepository.save(method));
    }

    public List<Agentconfigmethods> deleteMethodsByAgentId(Integer agentId) {
        List<Agentconfigmethods> methods = this.agentconfigmethodsRepository.findByAgentid(agentId);
        if (methods == null || methods.isEmpty()) {
            return null;
        }
        List<Agentconfigmethods> deletedMethods = new ArrayList<>(methods.size());
        for (Agentconfigmethods method : methods) {
            Agentconfigmethods deletedMethod = this.deleteMethodById(method.getMethodid());
            if (deletedMethod != null) {
                deletedMethods.add(deletedMethod);
            }
        }

        return deletedMethods;
    }

    public Agentconfigmethods deleteMethodById(Integer methodId) {
        // Check method existence
        Optional<Agentconfigmethods> method = this.agentconfigmethodsRepository.findById(methodId);
        if (!method.isPresent()) {
            return null;
        }

        // Delete config parameters which belong to this method
        agentconfigdetailsService.deleteDetailsByMethodId(methodId);
        // Delete response parameters which belong to this method
        agentresponseconfigService.deleteResponseByMethodId(methodId);

        // Delete method itself
        this.agentconfigmethodsRepository.deleteById(methodId);
        return method.get();
    }
}
