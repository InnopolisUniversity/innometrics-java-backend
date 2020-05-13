package com.innopolis.innometrics.activitiescollector.DTO;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ActivityReport implements Serializable {

    private Integer ActivityID;

    private String ActivityType;

    private Boolean idle_activity;

    private String userID;

    private Date start_time;

    private Date end_time;

    private String executable_name;

    private String browser_url;

    private String browser_title;

    private String ip_address;

    private String mac_address;

    private String pid;

    private String osversion;

    private Set<MeasurementReport> measurements = new HashSet<>();

    public ActivityReport() {
    }

    public ActivityReport(Integer activityID, String activityType, Boolean idle_activity, String userID, Date start_time, Date end_time, String executable_name, String browser_url, String browser_title, String ip_address, String mac_address, String pid, String osversion, Set<MeasurementReport> measurements) {
        ActivityID = activityID;
        ActivityType = activityType;
        this.idle_activity = idle_activity;
        this.userID = userID;
        this.start_time = start_time;
        this.end_time = end_time;
        this.executable_name = executable_name;
        this.browser_url = browser_url;
        this.browser_title = browser_title;
        this.ip_address = ip_address;
        this.mac_address = mac_address;
        this.pid = pid;
        this.osversion = osversion;
        this.measurements = measurements;
    }

    public Integer getActivityID() {
        return ActivityID;
    }

    public void setActivityID(Integer activityID) {
        ActivityID = activityID;
    }

    public String getActivityType() {
        return ActivityType;
    }

    public void setActivityType(String activityType) {
        ActivityType = activityType;
    }

    public Boolean getIdle_activity() {
        return idle_activity;
    }

    public void setIdle_activity(Boolean idle_activity) {
        this.idle_activity = idle_activity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
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

    public Set<MeasurementReport> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Set<MeasurementReport> measurements) {
        this.measurements = measurements;
    }
}
