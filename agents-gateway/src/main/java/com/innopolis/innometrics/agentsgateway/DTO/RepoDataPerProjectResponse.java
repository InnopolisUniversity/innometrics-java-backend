package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepoDataPerProjectResponse implements Serializable {
    private List<RepoResponseDTO> repos;

    public RepoDataPerProjectResponse() {
        repos = new ArrayList<>();
    }

    public RepoDataPerProjectResponse(List<RepoResponseDTO> repos) {
        this.repos = repos;
    }

    public List<RepoResponseDTO> getRepos() {
        return repos;
    }

    public void setRepos(List<RepoResponseDTO> repos) {
        this.repos = repos;
    }
}
