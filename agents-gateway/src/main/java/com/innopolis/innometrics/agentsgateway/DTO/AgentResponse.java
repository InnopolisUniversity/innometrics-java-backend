package com.innopolis.innometrics.agentsgateway.DTO;

import javax.persistence.Column;
import java.io.Serializable;

public class AgentResponse implements Serializable {
    private Integer agentid;
    private String agentname;
    private String description;
    private String oauthuri;
    private String authenticationmethod;

    public AgentResponse() {
    }

    public AgentResponse(Integer agentid, String agentname, String description, String oauthuri, String authenticationmethod) {
        this.agentid = agentid;
        this.agentname = agentname;
        this.description = description;
        this.oauthuri = oauthuri;
        this.authenticationmethod = authenticationmethod;
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

    public String getOauthuri() {
        return oauthuri;
    }

    public void setOauthuri(String oauthuri) {
        this.oauthuri = oauthuri;
    }

    public String getAuthenticationmethod() {
        return authenticationmethod;
    }

    public void setAuthenticationmethod(String authenticationmethod) {
        this.authenticationmethod = authenticationmethod;
    }
}
