package com.innopolis.innometrics.agentsgateway.service;

import com.innopolis.innometrics.agentsgateway.entity.Externalprojectxteam;
import com.innopolis.innometrics.agentsgateway.repository.ExternalprojectxteamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExternalprojectxteamService {
    @Autowired
    ExternalprojectxteamRepository externalprojectxteamRepository;

    public List<Externalprojectxteam> getExternalProjectTeamList() {
        return this.externalprojectxteamRepository.findAll();
    }

    public List<Externalprojectxteam> getExternalProjectTeamByAgentId(Integer agentId) {
        List<Externalprojectxteam> externalProjectTeamList = this.externalprojectxteamRepository.findAll();
        List<Externalprojectxteam> result = new ArrayList<>();
        for (Externalprojectxteam externalprojectxteam : externalProjectTeamList) {
            if (externalprojectxteam.getAgentid().equals(agentId)) {
                result.add(externalprojectxteam);
            }
        }
        return result;
    }

    public List<Externalprojectxteam> getExternalProjectTeamByTeamId(Integer teamId) {
        List<Externalprojectxteam> externalProjectTeamList = this.externalprojectxteamRepository.findAll();
        List<Externalprojectxteam> result = new ArrayList<>();
        for (Externalprojectxteam externalprojectxteam : externalProjectTeamList) {
            if (externalprojectxteam.getTeamid().equals(teamId)) {
                result.add(externalprojectxteam);
            }
        }
        return result;
    }

    public Externalprojectxteam getExternalProjectTeamById(Integer configId) {
        return this.externalprojectxteamRepository.findById(configId).orElse(null);
    }

    public Externalprojectxteam postExternalProjectTeam(Externalprojectxteam externalprojectxteam) throws Exception {
        return this.externalprojectxteamRepository.save(externalprojectxteam);
    }

    public Externalprojectxteam putExternalProjectTeam(Integer configId, Externalprojectxteam externalprojectxteam) throws Exception {
        return this.externalprojectxteamRepository.findById(configId).map(externalProjectTeam -> {
            externalProjectTeam.setAgentConfig(externalprojectxteam.getAgentConfig());
            externalProjectTeam.setAgentid(externalprojectxteam.getAgentid());
            // todo maybe pass external entity?
            externalProjectTeam.setTeamid(externalprojectxteam.getTeamid());
            externalProjectTeam.setRepoid(externalprojectxteam.getRepoid());
            externalProjectTeam.setIsactive(externalprojectxteam.getIsactive());

            return this.externalprojectxteamRepository.save(externalProjectTeam);
        }).orElseGet(() -> this.externalprojectxteamRepository.save(externalprojectxteam));
    }

    public List<Externalprojectxteam> deleteExternalProjectTeamByAgentId(Integer agentId) {
        return this.deleteExternalProjectList(this.getExternalProjectTeamByAgentId(agentId));
    }

    public List<Externalprojectxteam> deleteExternalProjectTeamByTeamId(Integer teamId) {
        return this.deleteExternalProjectList(this.getExternalProjectTeamByTeamId(teamId));
    }

    public Externalprojectxteam deleteExternalProjectTeamById(Integer configId) {
        Optional<Externalprojectxteam> externalProjectTeam = this.externalprojectxteamRepository.findById(configId);
        if (!externalProjectTeam.isPresent()) {
            return null;
        }
        this.externalprojectxteamRepository.deleteById(configId);
        return externalProjectTeam.get();
    }

    private List<Externalprojectxteam> deleteExternalProjectList(List<Externalprojectxteam> externalProjectList) {
        if (externalProjectList == null || externalProjectList.isEmpty()) {
            return null;
        }
        List<Externalprojectxteam> deletedExternalProjectsList = new ArrayList<>(externalProjectList.size());
        for (Externalprojectxteam externalprojectxteam : externalProjectList) {
            Externalprojectxteam deletedExternalProjectTeam = this.deleteExternalProjectTeamById(externalprojectxteam.getConfigid());
            if (deletedExternalProjectTeam != null) {
                deletedExternalProjectsList.add(deletedExternalProjectTeam);
            }
        }

        return deletedExternalProjectsList;
    }
}
