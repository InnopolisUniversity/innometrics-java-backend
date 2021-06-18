package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentdataconfig;
import com.innopolis.innometrics.agentsgateway.repository.AgentdataconfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentdataconfigService {
    @Autowired
    AgentdataconfigRepository agentdataconfigRepository;

    @Autowired
    AgentconfigService agentconfigService;

    public List<Agentdataconfig> getDataConfigList() {
        return agentdataconfigRepository.findAll();
    }

    public Agentdataconfig getDataConfig(Integer dataId) {
        return agentdataconfigRepository.findById(dataId).orElse(null);
    }

    public Agentconfig getAgent(Integer agentId) {
        return agentconfigService.getAgent(agentId);
    }

    public Agentdataconfig postDataConfig(Agentdataconfig agentdataconfig) {
        //todo set creation date manually?
        return agentdataconfigRepository.save(agentdataconfig);
    }

    public Agentdataconfig putDataConfig(Integer dataId, Agentdataconfig data) {
        return agentdataconfigRepository.findById(dataId).map(agentData -> {
            agentData.setAgentid(data.getAgentid());
            agentData.setAgentConfig(data.getAgentConfig());
            agentData.setSchemaname(data.getSchemaname());
            agentData.setTablename(data.getTablename());
            agentData.setEventdatefield(data.getEventdatefield());
            agentData.setEventauthorfield(data.getEventauthorfield());
            agentData.setEventdescriptionfield(data.getEventdescriptionfield());
            agentData.setEventtype(data.getEventtype());
            agentData.setIsactive(data.getIsactive());
            //todo set last updated date manually?

            agentdataconfigRepository.save(agentData);
            return agentData;
        }).orElseGet(() -> {
            agentdataconfigRepository.save(data);
            return data;
        });
    }

    public Agentdataconfig deleteDataConfig(Integer dataId) {
        Optional<Agentdataconfig> data = agentdataconfigRepository.findById(dataId);
        if (!data.isPresent()) {
            return null;
        }
        agentdataconfigRepository.deleteById(dataId);
        return data.get();
    }
}
