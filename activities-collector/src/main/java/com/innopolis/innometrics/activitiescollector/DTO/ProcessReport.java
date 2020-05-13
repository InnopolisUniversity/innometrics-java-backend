package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessReport implements Serializable {
    private String processName;

    private String userID;

    private String ip_address;

    private String mac_address;

    private Date collectedTime;

    private String pid;

    private String osversion;

    private List<MeasurementReport> measurementReportList;

    public ProcessReport() {
        measurementReportList = new ArrayList<>();
    }

    public ProcessReport(String processName, String userID, String ip_address, String mac_address, Date collectedTime, String pid, String osversion, List<MeasurementReport> measurementReportList) {
        this.processName = processName;
        this.userID = userID;
        this.ip_address = ip_address;
        this.mac_address = mac_address;
        this.collectedTime = collectedTime;
        this.pid = pid;
        this.osversion = osversion;
        this.measurementReportList = measurementReportList;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public Date getCollectedTime() {
        return collectedTime;
    }

    public void setCollectedTime(Date collectedTime) {
        this.collectedTime = collectedTime;
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

    public List<MeasurementReport> getMeasurementReportList() {
        return measurementReportList;
    }

    public void setMeasurementReportList(List<MeasurementReport> measurementReportList) {
        this.measurementReportList = measurementReportList;
    }
}
