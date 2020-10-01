package com.innopolis.innometrics.authserver.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectListRequest implements Serializable {
    List<ProjectRequest> projectList;

    public ProjectListRequest() {
        projectList = new ArrayList<>();
    }

    public ProjectListRequest(List<ProjectRequest> projectList) {
        this.projectList = projectList;
    }

    public List<ProjectRequest> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectRequest> projectList) {
        this.projectList = projectList;
    }

}
