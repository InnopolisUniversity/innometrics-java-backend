package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;
import java.util.Date;

public class ActivitiesReportByUserRequest implements Serializable {
    private String projectID;
    private String email;
    private Date min_Date;
    private Date max_Date;

    public ActivitiesReportByUserRequest() {
    }

    public ActivitiesReportByUserRequest(String projectID, String email, Date min_Date, Date max_Date) {
        this.projectID = projectID;
        this.email = email;
        this.min_Date = min_Date;
        this.max_Date = max_Date;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getMin_Date() {
        return min_Date;
    }

    public void setMin_Date(Date min_Date) {
        this.min_Date = min_Date;
    }

    public Date getMax_Date() {
        return max_Date;
    }

    public void setMax_Date(Date max_Date) {
        this.max_Date = max_Date;
    }
}
