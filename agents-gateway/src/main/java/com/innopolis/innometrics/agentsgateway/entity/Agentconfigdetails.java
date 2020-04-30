package com.innopolis.innometrics.agentsgateway.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "agentconfigdetails")
public class Agentconfigdetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configdetid", updatable = false)
    private Integer configDetId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "methodid")
    private Agentconfigmethods agentconfigmethods;

    @Column
    private String paramname;

    @Column
    private String paramtype;

    @Column
    private String isactive;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @Column(name = "updateby", insertable = false)
    private String updateby;

    public Agentconfigdetails() {
    }


    public Integer getConfigDetId() {
        return configDetId;
    }

    public void setConfigDetId(Integer configDetId) {
        this.configDetId = configDetId;
    }

    public Agentconfigmethods getAgentconfigmethods() {
        return agentconfigmethods;
    }

    public void setAgentconfigmethods(Agentconfigmethods agentconfigmethods) {
        this.agentconfigmethods = agentconfigmethods;
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
