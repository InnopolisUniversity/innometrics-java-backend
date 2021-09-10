package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Agentsxproject;
import com.innopolis.innometrics.agentsgateway.repository.AgentsxprojectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentsxprojectService {
    @Autowired
    AgentsxprojectRepository agentsxprojectRepository;

    public List<Agentsxproject> getAgentsProjectList() {
        return this.agentsxprojectRepository.findAll();
    }

    public List<Agentsxproject> getAgentsProjectByAgentId(Integer agentId) {
        List<Agentsxproject> agentsProjectList = this.agentsxprojectRepository.findAll();
        List<Agentsxproject> result = new ArrayList<>();
        for (Agentsxproject agentsxproject : agentsProjectList) {
            if (agentsxproject.getAgentid().equals(agentId)) {
                result.add(agentsxproject);
            }
        }
        return result;
    }

    public List<Agentsxproject> getAgentsProjectByProjectId(Integer projectId) {
        List<Agentsxproject> agentsProjectList = this.agentsxprojectRepository.findAll();
        List<Agentsxproject> result = new ArrayList<>();
        for (Agentsxproject agentsxproject : agentsProjectList) {
            if (agentsxproject.getProjectid().equals(projectId)) {
                result.add(agentsxproject);
            }
        }
        return result;
    }

    public Agentsxproject getAgentsProjectByAgentIdAndProjectId(Integer agentid, Integer projectid) {
        return this.agentsxprojectRepository.findByAgentidAndProjectid(agentid, projectid);
    }

    public Agentsxproject getAgentsProjectById(Integer configId) {
        return this.agentsxprojectRepository.findById(configId).orElse(null);
    }

    public Agentsxproject postAgentsProject(Agentsxproject agentsxproject) throws Exception {
        return this.agentsxprojectRepository.save(agentsxproject);
    }

    public Agentsxproject putAgentsProject(Integer configId, Agentsxproject agentsxproject) throws Exception {
        return this.agentsxprojectRepository.findById(configId).map(agentsProject -> {
            agentsProject.setAgentConfig(agentsxproject.getAgentConfig());
            agentsProject.setAgentid(agentsxproject.getAgentid());
            // todo maybe pass external entity?
            agentsProject.setProjectid(agentsxproject.getProjectid());
            agentsProject.setKey(agentsxproject.getKey());
            agentsProject.setToken(agentsxproject.getToken());
            agentsProject.setIsactive(agentsxproject.getIsactive());

            return this.agentsxprojectRepository.save(agentsProject);
        }).orElseGet(() -> this.agentsxprojectRepository.save(agentsxproject));
    }

    public List<Agentsxproject> deleteAgentsProjectByAgentId(Integer agentId) {
        return this.deleteAgentsProjectList(this.getAgentsProjectByAgentId(agentId));
    }

    public List<Agentsxproject> deleteAgentsProjectByProjectId(Integer projectId) {
        return this.deleteAgentsProjectList(this.getAgentsProjectByProjectId(projectId));
    }

    public Agentsxproject deleteAgentsProjectById(Integer configId) {
        Optional<Agentsxproject> agentsProject = this.agentsxprojectRepository.findById(configId);
        if (!agentsProject.isPresent()) {
            return null;
        }
        this.agentsxprojectRepository.deleteById(configId);
        return agentsProject.get();
    }

    private List<Agentsxproject> deleteAgentsProjectList(List<Agentsxproject> agentsProjectList) {
        if (agentsProjectList == null || agentsProjectList.isEmpty()) {
            return null;
        }
        List<Agentsxproject> deletedAgentsProjectsList = new ArrayList<>(agentsProjectList.size());
        for (Agentsxproject agentsxproject : agentsProjectList) {
            Agentsxproject deletedAgentsProject = this.deleteAgentsProjectById(agentsxproject.getConfigid());
            if (deletedAgentsProject != null) {
                deletedAgentsProjectsList.add(deletedAgentsProject);
            }
        }

        return deletedAgentsProjectsList;
    }
}
