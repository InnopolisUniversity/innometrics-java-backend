package com.innopolis.innometrics.agentsgateway.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "agentconfig")
public class Agentconfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer agentid;

    @Column
    private String agentname;

    @Column
    private String description;

    @Column
    private String sourcetype;

    @Column
    private String dbschemasource;

    @Column
    private String repoidfield;

    @Column
    private String oauthuri;

    @Column
    private String authenticationmethod;

    @Column
    private String apikey;

    @Column
    private String apisecret;

    @Column
    private String accesstokenendpoint;

    @Column
    private String authorizationbaseurl;

    @Column
    private String requesttokenendpoint;

    @Column
    private String isactive;

    @CreationTimestamp
    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @CreatedBy
    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @UpdateTimestamp
    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @LastModifiedBy
    @Column(name = "updateby", insertable = false)
    private String updateby;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "agentid")
    private Set<Agentconfigmethods> methods = new HashSet<Agentconfigmethods>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "agentid")
    private Set<Agentdataconfig> dataconfig = new HashSet<Agentdataconfig>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "agentid")
    private Set<Reposxproject> reposconfig = new HashSet<Reposxproject>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "agentid")
    private Set<Agentsxproject> agentsProject = new HashSet<Agentsxproject>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "agentid")
    private Set<Externalprojectxteam> externalProjectTeam = new HashSet<Externalprojectxteam>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "agentid")
    private Set<Agentsxcompany> agentsCompany = new HashSet<Agentsxcompany>();

    public Agentconfig() {
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

    public Set<Agentconfigmethods> getMethods() {
        return methods;
    }

    public void setMethods(Set<Agentconfigmethods> methods) {
        this.methods = methods;
    }

    public Set<Agentdataconfig> getDataconfig() {
        return dataconfig;
    }

    public void setDataconfig(Set<Agentdataconfig> dataconfig) {
        this.dataconfig = dataconfig;
    }

    public Set<Reposxproject> getReposconfig() {
        return reposconfig;
    }

    public void setReposconfig(Set<Reposxproject> reposconfig) {
        this.reposconfig = reposconfig;
    }

    public Set<Externalprojectxteam> getExternalProjectTeam() {
        return externalProjectTeam;
    }

    public Set<Agentsxproject> getAgentsProject() {
        return agentsProject;
    }

    public void setAgentsProject(Set<Agentsxproject> agentsProject) {
        this.agentsProject = agentsProject;
    }

    public void setExternalProjectTeam(Set<Externalprojectxteam> externalProject) {
        this.externalProjectTeam = externalProject;
    }

    public Set<Agentsxcompany> getAgentsCompany() {
        return agentsCompany;
    }

    public void setAgentsCompany(Set<Agentsxcompany> agentsCompany) {
        this.agentsCompany = agentsCompany;
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
