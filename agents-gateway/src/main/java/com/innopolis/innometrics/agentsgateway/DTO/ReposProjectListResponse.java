package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class ReposProjectListResponse {
    private List<ReposProjectDTO> reposProjectList;

    public ReposProjectListResponse() {
        this.reposProjectList = new ArrayList<>();
    }

    public List<ReposProjectDTO> getReposProjectList() {
        return reposProjectList;
    }

    public void setReposProjectList(List<ReposProjectDTO> reposProjectList) {
        this.reposProjectList = reposProjectList;
    }

    public void add(ReposProjectDTO reposProjectDTO) {
        this.reposProjectList.add(reposProjectDTO);
    }
}
