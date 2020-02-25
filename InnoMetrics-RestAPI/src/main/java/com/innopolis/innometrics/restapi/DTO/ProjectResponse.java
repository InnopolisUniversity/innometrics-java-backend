package com.innopolis.innometrics.restapi.DTO;

import com.innopolis.innometrics.restapi.entitiy.Project;

import java.util.Date;

public class ProjectResponse{
    private String projectID;
    private String name;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;

    public ProjectResponse() {
    }

    public ProjectResponse(Project project){
        this.projectID = project.getProjectID();
        this.name = project.getName();
        this.isactive = project.getIsactive();
        this.creationdate = project.getCreationdate();
        this.createdby = project.getCreatedby();
        this.lastupdate = project.getLastupdate();
        this.updateby = project.getUpdateby();
    }

    public ProjectResponse(String projectID, String name, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby) {
        this.projectID = projectID;
        this.name = name;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
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

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }
}
