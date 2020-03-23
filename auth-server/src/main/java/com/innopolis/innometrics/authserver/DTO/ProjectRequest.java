package com.innopolis.innometrics.authserver.DTO;

import java.io.Serializable;

public class ProjectRequest implements Serializable {
    private Integer projectID;
    private String name;
    private String isActive;

    public ProjectRequest() {
    }

    public ProjectRequest(Integer projectID, String name, String isActive) {
        this.projectID = projectID;
        this.name = name;
        this.isActive = isActive;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
