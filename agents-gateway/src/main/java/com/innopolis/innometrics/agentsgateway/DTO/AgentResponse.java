package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;
import java.util.Date;

public class AgentResponse implements Serializable {
    private Integer agentid;
    private String agentname;
    private String description;
    private String isconnected;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
    private String sourcetype;
    private String dbschemasource;
    private String repoidfield;
    private String oathuri;
    private String authenticationmethod;
    private String accesstokenendpoint;
    private String authorizationbaseurl;
    private String requesttokenendpoint;
    private String apikey;
    private String apisecret;

    public AgentResponse() {
    }

    public AgentResponse(Integer agentid, String agentname, String description, String isconnected) {
        this.agentid = agentid;
        this.agentname = agentname;
        this.description = description;
        this.isconnected = isconnected;
    }

    public AgentResponse(Integer agentid, String agentname, String description,
                         String isconnected, Date creationdate, String createdby,
                         Date lastupdate, String updateby, String sourcetype,
                         String dbschemasource, String repoidfield, String oathuri,
                         String authenticationmethod, String accesstokenendpoint,
                         String authorizationbaseurl, String requesttokenendpoint,
                         String apikey, String apisecret) {
        this.agentid = agentid;
        this.agentname = agentname;
        this.description = description;
        this.isconnected = isconnected;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
        this.sourcetype = sourcetype;
        this.dbschemasource = dbschemasource;
        this.repoidfield = repoidfield;
        this.oathuri = oathuri;
        this.authenticationmethod = authenticationmethod;
        this.accesstokenendpoint = accesstokenendpoint;
        this.authorizationbaseurl = authorizationbaseurl;
        this.requesttokenendpoint = requesttokenendpoint;
        this.apikey = apikey;
        this.apisecret = apisecret;
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

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public String getDbschemasource() {
        return dbschemasource;
    }

    public void setDbschemasource(String dbschemasource) {
        this.dbschemasource = dbschemasource;
    }

    public String getRepoidfield() {
        return repoidfield;
    }

    public void setRepoidfield(String repoidfield) {
        this.repoidfield = repoidfield;
    }

    public String getOathuri() {
        return oathuri;
    }

    public void setOathuri(String oathuri) {
        this.oathuri = oathuri;
    }

    public String getAuthenticationmethod() {
        return authenticationmethod;
    }

    public void setAuthenticationmethod(String authenticationmethod) {
        this.authenticationmethod = authenticationmethod;
    }

    public String getAccesstokenendpoint() {
        return accesstokenendpoint;
    }

    public void setAccesstokenendpoint(String accesstokenendpoint) {
        this.accesstokenendpoint = accesstokenendpoint;
    }

    public String getAuthorizationbaseurl() {
        return authorizationbaseurl;
    }

    public void setAuthorizationbaseurl(String authorizationbaseurl) {
        this.authorizationbaseurl = authorizationbaseurl;
    }

    public String getRequesttokenendpoint() {
        return requesttokenendpoint;
    }

    public void setRequesttokenendpoint(String requesttokenendpoint) {
        this.requesttokenendpoint = requesttokenendpoint;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }
}
