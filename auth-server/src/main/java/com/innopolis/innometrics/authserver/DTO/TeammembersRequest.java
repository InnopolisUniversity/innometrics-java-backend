package com.innopolis.innometrics.authserver.DTO;

import java.util.Date;

public class TeammembersRequest {
    private Integer memberid;

    private Integer teamid;

    private String email;

    private String isactive;

    private Date creationdate;

    private String createdby;

    private Date lastupdate;

    private String updateby;

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public TeammembersRequest(){}

    public TeammembersRequest(Integer memberid, Integer teamid, String email, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby) {
        this.memberid = memberid;
        this.teamid = teamid;
        this.email = email;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
    }
}
