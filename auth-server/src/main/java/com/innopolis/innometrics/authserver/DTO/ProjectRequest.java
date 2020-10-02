package com.innopolis.innometrics.authserver.DTO;

import java.io.Serializable;

public class ProjectRequest implements Serializable {
    private Integer projectID;
    private String name;
    private String isactive;

    public ProjectRequest() {
    }

    public ProjectRequest(Integer projectID, String name, String isactive) {
        this.projectID = projectID;
        this.name = name;
        this.isactive = isactive;
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

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isActive) {
        this.isactive = isActive;
    }
}
