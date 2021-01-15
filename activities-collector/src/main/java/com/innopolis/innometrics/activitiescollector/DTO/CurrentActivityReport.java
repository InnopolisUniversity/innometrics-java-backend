package com.innopolis.innometrics.activitiescollector.DTO;

import java.util.ArrayList;
import java.util.List;

public class CurrentActivityReport {
    private List<String> AppList;
    private List<String> MacAddressList;

    public CurrentActivityReport() {
        AppList = new ArrayList<>();
        MacAddressList = new ArrayList<>();
    }

    public CurrentActivityReport(List<String> appList, List<String> macAddressList) {
        AppList = appList;
        MacAddressList = macAddressList;
    }

    public List<String> getAppList() {
        return AppList;
    }

    public void setAppList(List<String> appList) {
        AppList = appList;
    }

    public List<String> getMacAddressList() {
        return MacAddressList;
    }

    public void setMacAddressList(List<String> macAddressList) {
        MacAddressList = macAddressList;
    }
}
