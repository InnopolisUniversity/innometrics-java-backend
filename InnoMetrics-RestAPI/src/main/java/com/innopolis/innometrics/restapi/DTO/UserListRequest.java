package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;

public class UserListRequest implements Serializable {
    private String projectID;


    public UserListRequest() {
    }

    public UserListRequest(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
}
