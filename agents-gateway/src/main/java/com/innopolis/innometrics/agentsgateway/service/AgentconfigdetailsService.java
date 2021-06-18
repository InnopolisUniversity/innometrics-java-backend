package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigdetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentconfigdetailsService {
    @Autowired
    AgentconfigdetailsRepository agentconfigdetailsRepository;

    @Autowired
    AgentconfigmethodsService agentconfigmethodsService;

    public List<Agentconfigdetails> getDetailsList() {
        return agentconfigdetailsRepository.findAll();
    }

    public Agentconfigdetails getConfigDetails(Integer detailsId) {
        return agentconfigdetailsRepository.findById(detailsId).orElse(null);
    }

    public Agentconfigmethods getMethod(Integer methodid) {
        return agentconfigmethodsService.getMethodByMethodId(methodid);
    }

    public Agentconfigdetails postConfigDetails(Agentconfigdetails agentconfigdetails) {
        //todo set creation date manually?
        return agentconfigdetailsRepository.save(agentconfigdetails);
    }

    public Agentconfigdetails putConfigDetails(Integer detailsId, Agentconfigdetails details) {
        return agentconfigdetailsRepository.findById(detailsId).map(agentDetails -> {
            agentDetails.setAgentconfigmethods(details.getAgentconfigmethods());
            agentDetails.setParamname(details.getParamname());
            agentDetails.setParamtype(details.getParamtype());
            agentDetails.setRequestparam(details.getRequestparam());
            agentDetails.setRequesttype(details.getRequesttype());
            agentDetails.setIsactive(details.getIsactive());
            agentDetails.setDefaultvalue(details.getDefaultvalue());
            //todo set last updated date manually?

            agentconfigdetailsRepository.save(agentDetails);
            return agentDetails;
        }).orElseGet(() -> {
            agentconfigdetailsRepository.save(details);
            return details;
        });
    }

    public Agentconfigdetails deleteConfigDetails(Integer detailsId) {
        Optional<Agentconfigdetails> details = agentconfigdetailsRepository.findById(detailsId);
        if (!details.isPresent()) {
            return null;
        }
        agentconfigdetailsRepository.deleteById(detailsId);
        return details.get();
    }
}
