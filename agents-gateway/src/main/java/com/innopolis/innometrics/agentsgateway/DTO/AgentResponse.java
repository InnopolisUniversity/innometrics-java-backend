package com.innopolis.innometrics.agentsgateway.DTO;

import javax.persistence.Column;
import java.io.Serializable;

public class AgentResponse implements Serializable {
    private Integer agentid;
    private String agentname;
    private String description;
    private String isconnected;

    public AgentResponse() {
    }

    public AgentResponse(Integer agentid, String agentname, String description, String isconnected) {
        this.agentid = agentid;
        this.agentname = agentname;
        this.description = description;
        this.isconnected = isconnected;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsconnected() {
        return isconnected;
    }

    public void setIsconnected(String isconnected) {
        this.isconnected = isconnected;
    }
}
