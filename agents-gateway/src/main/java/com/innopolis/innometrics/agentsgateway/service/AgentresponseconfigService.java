package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
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

    @Autowired
    AgentconfigmethodsService agentconfigmethodsService;

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

    public Agentconfigmethods getMethod(Integer methodid) {
        return this.agentconfigmethodsService.getMethodById(methodid);
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

            this.agentresponseconfigRepository.save(agentDetails);
            return agentDetails;
        }).orElseGet(() -> {
            this.agentresponseconfigRepository.save(response);
            return response;
        });
    }

    public Agentresponseconfig deleteResponse(Integer responseId) {
        Optional<Agentresponseconfig> details = this.agentresponseconfigRepository.findById(responseId);
        if (!details.isPresent()) {
            return null;
        }
        this.agentresponseconfigRepository.deleteById(responseId);
        return details.get();
    }
}
