package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentresponseconfig;
import com.innopolis.innometrics.agentsgateway.repository.AgentresponseconfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentresponseconfigService {
    @Autowired
    AgentresponseconfigRepository agentresponseconfigRepository;

    public List<Agentresponseconfig> getResponsesList() {
        return this.agentresponseconfigRepository.findAll();
    }

    public List<Agentresponseconfig> getResponsesByMethodId(Integer methodId) {
        List<Agentresponseconfig> responsesList = this.agentresponseconfigRepository.findAll();
        List<Agentresponseconfig> result = new ArrayList<>();
        for (Agentresponseconfig agentresponseconfig : responsesList) {
            if (agentresponseconfig.getAgentconfigmethods().getMethodid().equals(methodId)) {
                result.add(agentresponseconfig);
            }
        }
        return result;
    }

    public Agentresponseconfig getResponseById(Integer responseId) {
        return this.agentresponseconfigRepository.findById(responseId).orElse(null);
    }

    public Agentresponseconfig postResponse(Agentresponseconfig agentresponseconfig) {
        return this.agentresponseconfigRepository.save(agentresponseconfig);
    }

    public Agentresponseconfig putResponse(Integer responseId, Agentresponseconfig response) {
        return this.agentresponseconfigRepository.findById(responseId).map(agentDetails -> {
            agentDetails.setAgentconfigmethods(response.getAgentconfigmethods());
            agentDetails.setResponseparam(response.getResponseparam());
            agentDetails.setParamname(response.getParamname());
            agentDetails.setParamtype(response.getParamtype());
            agentDetails.setIsactive(response.getIsactive());

            return this.agentresponseconfigRepository.save(agentDetails);
        }).orElseGet(() -> this.agentresponseconfigRepository.save(response));
    }

    public List<Agentresponseconfig> deleteResponseByMethodId(Integer methodId) {
        List<Agentresponseconfig> responsesList = this.getResponsesByMethodId(methodId);
        if (responsesList == null || responsesList.isEmpty()) {
            return null;
        }
        List<Agentresponseconfig> deletedResponsesList = new ArrayList<>(responsesList.size());
        for (Agentresponseconfig response : responsesList) {
            Agentresponseconfig deletedResponse = this.deleteResponseById(response.getConfigresponseid());
            if (deletedResponse != null) {
                deletedResponsesList.add(deletedResponse);
            }
        }

        return deletedResponsesList;
    }

    public Agentresponseconfig deleteResponseById(Integer responseId) {
        Optional<Agentresponseconfig> response = this.agentresponseconfigRepository.findById(responseId);
        if (!response.isPresent()) {
            return null;
        }
        this.agentresponseconfigRepository.deleteById(responseId);
        return response.get();
    }
}
