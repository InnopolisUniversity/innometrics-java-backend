package com.innopolis.innometrics.activitiescollector.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "process")
public class Process implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer ProcessID;

    @Column
    private String email;

    @Column
    private Integer projectid;

    @Column
    private String executable_name;

    @Column
    private String pid;

    @Column
    private String osversion;

    @Column
    private Date collectedtime;

    @Column
    private String ip_address;

    @Column
    private String mac_address;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @Column(name = "updateby", insertable = false)
    private String updateby;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ProcessID")
    private List<ProcessMeasurement> ProceMeasurements;

    public Process() {
        ProceMeasurements = new ArrayList<>();
    }

    public Integer getProcessID() {
        return ProcessID;
    }

    public void setProcessID(Integer processID) {
        ProcessID = processID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getExecutable_name() {
        return executable_name;
    }

    public void setExecutable_name(String executable_name) {
        this.executable_name = executable_name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Date getCollectedtime() {
        return collectedtime;
    }

    public void setCollectedtime(Date collectedtime) {
        this.collectedtime = collectedtime;
    }

    public String getOsversion() {
        return osversion;
    }

    public void setOsversion(String osversion) {
        this.osversion = osversion;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
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

    public List<ProcessMeasurement> getProceMeasurements() {
        return ProceMeasurements;
    }

    public void setProceMeasurements(List<ProcessMeasurement> measurements) {
        this.ProceMeasurements = measurements;
    }
}
