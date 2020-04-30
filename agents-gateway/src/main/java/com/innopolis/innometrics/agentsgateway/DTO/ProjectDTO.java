package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;

public class ProjectDTO implements Serializable {
    private String ProjectId;
    private String ProjectName;
    private String reference;

    public ProjectDTO() {
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
