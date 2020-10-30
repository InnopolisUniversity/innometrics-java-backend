package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class BugTrackingRequest implements Serializable {

    private Integer bugId;

    private String username;

    private String title;

    private String classOfBug;

    private Integer line;

    private String comment;

    private String trace;

    private String os;

    private String dataCollectorVersion;

    private Timestamp creationdate;

    private Timestamp lastupdate;

    // 0 - not solved, 1 - solved
    private boolean status;

    public BugTrackingRequest(Integer bugId, String username, String title, String classOfBug, Integer line, String comment, String trace, String os, String dataCollectorVersion, Timestamp lastupdate, boolean status) {
        this.bugId = bugId;
        this.username = username;
        this.title = title;
        this.classOfBug = classOfBug;
        this.line = line;
        this.comment = comment;
        this.trace = trace;
        this.os = os;
        this.dataCollectorVersion = dataCollectorVersion;
        this.lastupdate = lastupdate;
        this.status = status;
    }

    public BugTrackingRequest() {
    }

    public Integer getBugId() {
        return bugId;
    }

    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassOfBug() {
        return classOfBug;
    }

    public void setClassOfBug(String classOfBug) {
        this.classOfBug = classOfBug;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDataCollectorVersion() {
        return dataCollectorVersion;
    }

    public void setDataCollectorVersion(String dataCollectorVersion) {
        this.dataCollectorVersion = dataCollectorVersion;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Timestamp creationdate) {
        this.creationdate = creationdate;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Timestamp lastupdate) {
        this.lastupdate = lastupdate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
