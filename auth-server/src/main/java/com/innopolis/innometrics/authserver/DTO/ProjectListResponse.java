package com.innopolis.innometrics.authserver.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectListResponse implements Serializable {
    List<ProjectResponse> projectList;

    public ProjectListResponse() {
        projectList = new ArrayList<>();
    }

    public ProjectListResponse(List<ProjectResponse> projectList) {
        this.projectList = projectList;
    }

    public List<ProjectResponse> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectResponse> projectList) {
        this.projectList = projectList;
    }
}
