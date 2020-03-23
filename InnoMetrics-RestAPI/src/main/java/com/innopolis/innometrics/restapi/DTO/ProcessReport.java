package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProcessReport implements Serializable {
    private String processName;

    private String userID;

    private String ip_address;

    private String mac_address;

    private List<MeasurementReport> measurementReportList;

    public ProcessReport() {
        measurementReportList = new ArrayList<>();
    }

    public ProcessReport(String processName, String userID, String ip_address, String mac_address, List<MeasurementReport> measurementReportList) {
        this.processName = processName;
        this.userID = userID;
        this.ip_address = ip_address;
        this.mac_address = mac_address;
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

    public List<MeasurementReport> getMeasurementReportList() {
        return measurementReportList;
    }

    public void setMeasurementReportList(List<MeasurementReport> measurementReportList) {
        this.measurementReportList = measurementReportList;
    }
}
