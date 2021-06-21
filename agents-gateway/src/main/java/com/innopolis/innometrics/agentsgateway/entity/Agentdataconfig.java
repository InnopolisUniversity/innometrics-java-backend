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
@Table(name = "agent_data_config")
public class Agentdataconfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer datacofingid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agentid")
    private Agentconfig agentConfig;

    @Column(insertable = false, updatable = false)
    private Integer agentid;

    @Column
    private String schemaname;

    @Column
    private String tablename;

    @Column
    private String eventdatefield;

    @Column
    private String eventauthorfield;

    @Column
    private String eventdescriptionfield;

    @Column
    private String eventtype;

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

    public Agentdataconfig() {
    }

    public Integer getDatacofingid() {
        return datacofingid;
    }

    public void setDatacofingid(Integer datacofingid) {
        this.datacofingid = datacofingid;
    }

    public Agentconfig getAgentConfig() {
        return agentConfig;
    }

    public void setAgentConfig(Agentconfig agentConfig) {
        this.agentConfig = agentConfig;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public String getSchemaname() {
        return schemaname;
    }

    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getEventdatefield() {
        return eventdatefield;
    }

    public void setEventdatefield(String eventdatefield) {
        this.eventdatefield = eventdatefield;
    }

    public String getEventauthorfield() {
        return eventauthorfield;
    }

    public void setEventauthorfield(String eventauthorfield) {
        this.eventauthorfield = eventauthorfield;
    }

    public String getEventdescriptionfield() {
        return eventdescriptionfield;
    }

    public void setEventdescriptionfield(String eventdescriptionfield) {
        this.eventdescriptionfield = eventdescriptionfield;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
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
