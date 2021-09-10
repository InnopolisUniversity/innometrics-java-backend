package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;
import java.util.Date;

public class DataConfigDTO implements Serializable {
    private Integer datacofingid;
    private Integer agentid;
    private String schemaname;
    private String tablename;
    private String eventdatefield;
    private String eventauthorfield;
    private String eventdescriptionfield;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
    private String eventtype;

    public DataConfigDTO() {
    }

    public DataConfigDTO(Integer datacofingid, Integer agentid, String schemaname, String tablename,
                         String eventdatefield, String eventauthorfield, String eventdescriptionfield,
                         String isactive, Date creationdate, String createdby, Date lastupdate,
                         String updateby, String eventtype) {
        this.datacofingid = datacofingid;
        this.agentid = agentid;
        this.schemaname = schemaname;
        this.tablename = tablename;
        this.eventdatefield = eventdatefield;
        this.eventauthorfield = eventauthorfield;
        this.eventdescriptionfield = eventdescriptionfield;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
        this.eventtype = eventtype;
    }

    public Integer getDatacofingid() {
        return datacofingid;
    }

    public void setDatacofingid(Integer datacofingid) {
        this.datacofingid = datacofingid;
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

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }
}
