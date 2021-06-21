package com.innopolis.innometrics.agentsgateway.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "agentresponseconfig")
public class Agentresponseconfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configresponseid", updatable = false)
    private Integer configresponseid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "methodid")
    private Agentconfigmethods agentconfigmethods;

    @Column
    private String responseparam;

    @Column
    private String paramname;

    @Column
    private String paramtype;

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

    public Agentresponseconfig() {
    }


    public Integer getConfigresponseid() {
        return configresponseid;
    }

    public void setConfigresponseid(Integer configresponseid) {
        this.configresponseid = configresponseid;
    }

    public Agentconfigmethods getAgentconfigmethods() {
        return agentconfigmethods;
    }

    public void setAgentconfigmethods(Agentconfigmethods agentconfigmethods) {
        this.agentconfigmethods = agentconfigmethods;
    }

    public String getResponseparam() {
        return responseparam;
    }

    public void setResponseparam(String responseparam) {
        this.responseparam = responseparam;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public String getParamtype() {
        return paramtype;
    }

    public void setParamtype(String paramtype) {
        this.paramtype = paramtype;
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
