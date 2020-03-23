package com.innopolis.innometrics.authserver.DTO;

import java.io.Serializable;

public class UserListRequest implements Serializable {
    private String projectID;


    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public UserListRequest(String projectID) {
        this.projectID = projectID;
    }

    public UserListRequest() {
    }
}
