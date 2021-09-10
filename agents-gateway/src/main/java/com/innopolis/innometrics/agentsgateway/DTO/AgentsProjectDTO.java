package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.Date;

public class AgentsProjectDTO {
    private Integer configid;
    private Integer agentid;
    private Integer projectid;
    private String key;
    private String token;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;

    public AgentsProjectDTO() {
    }

    public AgentsProjectDTO(Integer configid, Integer agentid, Integer projectid, String key, String token,
                            String isactive, Date creationdate, String createdby, Date lastupdate, String updateby) {
        this.configid = configid;
        this.agentid = agentid;
        this.projectid = projectid;
        this.key = key;
        this.token = token;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
    }

    public Integer getConfigid() {
        return configid;
    }

    public void setConfigid(Integer configid) {
        this.configid = configid;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
