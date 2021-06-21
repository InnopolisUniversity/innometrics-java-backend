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
@Table(name = "agentconfigmethods")
public class Agentconfigmethods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "methodid", updatable = false)
    private Integer methodid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agentid")
    private Agentconfig agentConfig;

    @Column(insertable = false, updatable = false)
    private Integer agentid;

    @Column
    private String endpoint;

    @Column
    private String description;

    @Column
    private String operation;

    @Column
    private String requesttype;

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
    @JoinColumn(name = "methodid")
    private Set<Agentconfigdetails> params = new HashSet<Agentconfigdetails>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "methodid")
    private Set<Agentresponseconfig> ResponseParams = new HashSet<Agentresponseconfig>();

    public Agentconfigmethods() {
    }

    public Integer getMethodid() {
        return methodid;
    }

    public void setMethodid(Integer methodid) {
        this.methodid = methodid;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public Agentconfig getAgentConfig() {
        return agentConfig;
    }

    public void setAgentConfig(Agentconfig agentConfig) {
        this.agentConfig = agentConfig;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
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

    public Set<Agentconfigdetails> getParams() {
        return params;
    }

    public void setParams(Set<Agentconfigdetails> params) {
        this.params = params;
    }

    public Set<Agentresponseconfig> getResponseParams() {
        return ResponseParams;
    }

    public void setResponseParams(Set<Agentresponseconfig> responseParams) {
        ResponseParams = responseParams;
    }
}
