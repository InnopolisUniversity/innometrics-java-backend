package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;

public class RepoDataPerProjectRequest implements Serializable {
    private Integer projectId;

    public RepoDataPerProjectRequest() {
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
