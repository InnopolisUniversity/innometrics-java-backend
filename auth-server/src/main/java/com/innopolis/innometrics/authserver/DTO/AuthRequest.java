package com.innopolis.innometrics.authserver.DTO;

public class AuthRequest {

    private String email;

    private String password;

    private String projectID;

    public AuthRequest() {
    }

    public AuthRequest(String email, String password, String projectID) {
        this.email = email;
        this.password = password;
        this.projectID = projectID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
}
