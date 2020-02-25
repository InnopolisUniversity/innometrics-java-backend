package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;

public class ProjectRequest implements Serializable {
    private String projectID;

    private String name;

    public ProjectRequest() {
    }

    public ProjectRequest(String projectID, String name) {
        this.projectID = projectID;
        this.name = name;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
