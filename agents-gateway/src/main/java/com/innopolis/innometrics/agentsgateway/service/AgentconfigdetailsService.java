package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfigdetails;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigdetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentconfigdetailsService {
    @Autowired
    AgentconfigdetailsRepository agentconfigdetailsRepository;

    public List<Agentconfigdetails> getDetailsList() {
        return this.agentconfigdetailsRepository.findAll();
    }

    public List<Agentconfigdetails> getDetailsByMethodId(Integer methodId) {
        List<Agentconfigdetails> detailsList = this.agentconfigdetailsRepository.findAll();
        List<Agentconfigdetails> result = new ArrayList<>();
        for (Agentconfigdetails agentconfigdetails : detailsList) {
            if (agentconfigdetails.getAgentconfigmethods().getMethodid().equals(methodId)) {
                result.add(agentconfigdetails);
            }
        }
        return result;
    }

    public Agentconfigdetails getDetailsById(Integer detailsId) {
        return this.agentconfigdetailsRepository.findById(detailsId).orElse(null);
    }

    public Agentconfigdetails postDetails(Agentconfigdetails agentconfigdetails) {
        return this.agentconfigdetailsRepository.save(agentconfigdetails);
    }

    public Agentconfigdetails putDetails(Integer detailsId, Agentconfigdetails details) {
        return this.agentconfigdetailsRepository.findById(detailsId).map(agentDetails -> {
            agentDetails.setAgentconfigmethods(details.getAgentconfigmethods());
            agentDetails.setParamname(details.getParamname());
            agentDetails.setParamtype(details.getParamtype());
            agentDetails.setRequestparam(details.getRequestparam());
            agentDetails.setRequesttype(details.getRequesttype());
            agentDetails.setIsactive(details.getIsactive());
            agentDetails.setDefaultvalue(details.getDefaultvalue());

            return this.agentconfigdetailsRepository.save(agentDetails);
        }).orElseGet(() -> this.agentconfigdetailsRepository.save(details));
    }

    public List<Agentconfigdetails> deleteDetailsByMethodId(Integer methodId) {
        List<Agentconfigdetails> detailsList = this.getDetailsByMethodId(methodId);
        if (detailsList == null || detailsList.isEmpty()) {
            return null;
        }
        List<Agentconfigdetails> deletedDetailsList = new ArrayList<>(detailsList.size());
        for (Agentconfigdetails details : detailsList) {
            Agentconfigdetails deletedDetails = this.deleteDetailsById(details.getConfigDetId());
            if (deletedDetails != null) {
                deletedDetailsList.add(deletedDetails);
            }
        }

        return deletedDetailsList;
    }

    public Agentconfigdetails deleteDetailsById(Integer detailsId) {
        Optional<Agentconfigdetails> details = this.agentconfigdetailsRepository.findById(detailsId);
        if (!details.isPresent()) {
            return null;
        }
        this.agentconfigdetailsRepository.deleteById(detailsId);
        return details.get();
    }
}
