package com.innopolis.innometrics.activitiescollector.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table
public class Activity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer ActivityID;

    @Column
    private String activitytype;

    @Column
    private Boolean idle_activity;

    @Column
    private String email;

    @Column(name = "start_time")
    private Date starttime;

    @Column
    private Date end_time;

    @Column
    private String executable_name;

    @Column
    private String browser_url;

    @Column
    private String browser_title;

    @Column
    private String pid;

    @Column
    private String osversion;

    @Column
    private String ip_address;

    @Column
    private String mac_address;

    @Column(nullable = true)
    private String value;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @Column(name = "updateby", insertable = false)
    private String updateby;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ActivityID")
    private Set<Measurement> measurements = new HashSet<Measurement>();

    public Activity() {

    }

    public Integer getActivityID() {
        return ActivityID;
    }

    public void setActivityID(Integer activityID) {
        ActivityID = activityID;
    }

    public String getActivitytype() {
        return activitytype;
    }

    public void setActivitytype(String activityType) {
        activitytype = activityType;
    }

    public Boolean getIdle_activity() {
        return idle_activity;
    }

    public void setIdle_activity(Boolean idle_activity) {
        this.idle_activity = idle_activity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getExecutable_name() {
        return executable_name;
    }

    public void setExecutable_name(String executable_name) {
        this.executable_name = executable_name;
    }

    public String getBrowser_url() {
        return browser_url;
    }

    public void setBrowser_url(String browser_url) {
        this.browser_url = browser_url;
    }

    public String getBrowser_title() {
        return browser_title;
    }

    public void setBrowser_title(String browser_title) {
        this.browser_title = browser_title;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Set<Measurement> getMeasurements() { return measurements; }

    public void setMeasurements(Set<Measurement> measurements) {
        this.measurements = measurements;
    }
}
