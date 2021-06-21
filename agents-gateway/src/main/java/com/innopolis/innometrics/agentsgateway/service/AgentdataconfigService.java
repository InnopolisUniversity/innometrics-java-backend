package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentdataconfig;
import com.innopolis.innometrics.agentsgateway.repository.AgentdataconfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentdataconfigService {
    @Autowired
    AgentdataconfigRepository agentdataconfigRepository;

    @Autowired
    AgentconfigService agentconfigService;

    public List<Agentdataconfig> getDataList() {
        return this.agentdataconfigRepository.findAll();
    }

    public List<Agentdataconfig> getDataAgentsByAgentId(Integer agentId) {
        List<Agentdataconfig> dataList = this.agentdataconfigRepository.findAll();
        List<Agentdataconfig> result = new ArrayList<>();
        for (Agentdataconfig agentdataconfig : dataList) {
            if (agentdataconfig.getAgentid().equals(agentId)) {
                result.add(agentdataconfig);
            }
        }
        return result;
    }

    public Agentdataconfig getDataById(Integer dataId) {
        return this.agentdataconfigRepository.findById(dataId).orElse(null);
    }

    public Agentconfig getAgent(Integer agentId) {
        return this.agentconfigService.getAgentById(agentId);
    }

    public Agentdataconfig postData(Agentdataconfig agentdataconfig) {
        return this.agentdataconfigRepository.save(agentdataconfig);
    }

    public Agentdataconfig putData(Integer dataId, Agentdataconfig data) {
        return this.agentdataconfigRepository.findById(dataId).map(agentData -> {
            agentData.setAgentid(data.getAgentid());
            agentData.setAgentConfig(data.getAgentConfig());
            agentData.setSchemaname(data.getSchemaname());
            agentData.setTablename(data.getTablename());
            agentData.setEventdatefield(data.getEventdatefield());
            agentData.setEventauthorfield(data.getEventauthorfield());
            agentData.setEventdescriptionfield(data.getEventdescriptionfield());
            agentData.setEventtype(data.getEventtype());
            agentData.setIsactive(data.getIsactive());

            this.agentdataconfigRepository.save(agentData);
            return agentData;
        }).orElseGet(() -> {
            this.agentdataconfigRepository.save(data);
            return data;
        });
    }

    public Agentdataconfig deleteDataConfig(Integer dataId) {
        Optional<Agentdataconfig> data = this.agentdataconfigRepository.findById(dataId);
        if (!data.isPresent()) {
            return null;
        }
        this.agentdataconfigRepository.deleteById(dataId);
        return data.get();
    }
}
