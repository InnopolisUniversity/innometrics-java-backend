package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.entity.Agentresponseconfig;
import com.innopolis.innometrics.agentsgateway.repository.AgentresponseconfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentresponseconfigService {
    @Autowired
    AgentresponseconfigRepository agentresponseconfigRepository;

    @Autowired
    AgentconfigmethodsService agentconfigmethodsService;

    public List<Agentresponseconfig> getResponseList() {
        return agentresponseconfigRepository.findAll();
    }

    public Agentresponseconfig getResponse(Integer responseId) {
        return agentresponseconfigRepository.findById(responseId).orElse(null);
    }

    public Agentconfigmethods getMethod(Integer methodid) {
        return agentconfigmethodsService.getMethodByMethodId(methodid);
    }

    public Agentresponseconfig postResponse(Agentresponseconfig agentresponseconfig) {
        //todo set creation date manually?
        return agentresponseconfigRepository.save(agentresponseconfig);
    }

    public Agentresponseconfig putResponse(Integer responseId, Agentresponseconfig response) {
        return agentresponseconfigRepository.findById(responseId).map(agentDetails -> {
            agentDetails.setAgentconfigmethods(response.getAgentconfigmethods());
            agentDetails.setResponseparam(response.getResponseparam());
            agentDetails.setParamname(response.getParamname());
            agentDetails.setParamtype(response.getParamtype());
            agentDetails.setIsactive(response.getIsactive());
            //todo set last updated date manually?

            agentresponseconfigRepository.save(agentDetails);
            return agentDetails;
        }).orElseGet(() -> {
            agentresponseconfigRepository.save(response);
            return response;
        });
    }

    public Agentresponseconfig deleteResponse(Integer responseId) {
        Optional<Agentresponseconfig> details = agentresponseconfigRepository.findById(responseId);
        if (!details.isPresent()) {
            return null;
        }
        agentresponseconfigRepository.deleteById(responseId);
        return details.get();
    }
}
