package com.innopolis.innometrics.agentsgateway.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innopolis.innometrics.agentsgateway.entity.Reposxproject;
import com.innopolis.innometrics.agentsgateway.repository.ReposxprojectRepository;

@Service
public class ReposxprojectService {
    @Autowired
    ReposxprojectRepository reposxprojectRepository;

    public List<Reposxproject> getReposProjectList() {
        return this.reposxprojectRepository.findAll();
    }

    public List<Reposxproject> getReposProjectByAgentId(Integer agentId) {
        List<Reposxproject> reposProjectList = this.reposxprojectRepository.findAll();
        List<Reposxproject> result = new ArrayList<>();
        for (Reposxproject reposxproject : reposProjectList) {
            if (reposxproject.getAgentid().equals(agentId)) {
                result.add(reposxproject);
            }
        }
        return result;
    }

    public List<Reposxproject> getReposProjectByProjectId(Integer projectId) {
        return this.reposxprojectRepository.findByProjectid(projectId);
    }

    public Reposxproject getReposProjectById(Integer configId) {
        return this.reposxprojectRepository.findById(configId).orElse(null);
    }

    public Reposxproject postReposProject(Reposxproject reposxproject) throws Exception {
        return this.reposxprojectRepository.save(reposxproject);
    }

    public Reposxproject putReposProject(Integer configId, Reposxproject reposxproject) throws Exception {
        return this.reposxprojectRepository.findById(configId).map(reposProject -> {
            reposProject.setAgentid(reposxproject.getAgentid());
            // todo maybe pass external entity?
            reposProject.setProjectid(reposxproject.getProjectid());
            reposProject.setRepoid(reposxproject.getRepoid());
            reposProject.setIsactive(reposxproject.getIsactive());

            return this.reposxprojectRepository.save(reposProject);
        }).orElseGet(() -> this.reposxprojectRepository.save(reposxproject));
    }

    public List<Reposxproject> deleteReposProjectByAgentId(Integer agentId) {
        return this.deleteReposProjectList(this.getReposProjectByAgentId(agentId));
    }

    public List<Reposxproject> deleteReposProjectByProjectId(Integer projectId) {
        return this.deleteReposProjectList(this.getReposProjectByProjectId(projectId));
    }

    public Reposxproject deleteReposProjectById(Integer configId) {
        Optional<Reposxproject> reposProject = this.reposxprojectRepository.findById(configId);
        if (!reposProject.isPresent()) {
            return null;
        }
        this.reposxprojectRepository.deleteById(configId);
        return reposProject.get();
    }

    private List<Reposxproject> deleteReposProjectList(List<Reposxproject> reposProjectList) {
        if (reposProjectList == null || reposProjectList.isEmpty()) {
            return null;
        }
        List<Reposxproject> deletedReposProjectsList = new ArrayList<>(reposProjectList.size());
        for (Reposxproject reposxproject : reposProjectList) {
            Reposxproject deletedReposProject = this.deleteReposProjectById(reposxproject.getConfigid());
            if (deletedReposProject != null) {
                deletedReposProjectsList.add(deletedReposProject);
            }
        }

        return deletedReposProjectsList;
    }
}
