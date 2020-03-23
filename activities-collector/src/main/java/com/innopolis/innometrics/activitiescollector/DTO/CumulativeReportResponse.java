package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CumulativeReportResponse implements Serializable {
    private List<CumulativeActivityReport> activityReports;

    public CumulativeReportResponse() {
        activityReports = new ArrayList<>();
    }

    public CumulativeReportResponse(List<CumulativeActivityReport> activityReports) {
        this.activityReports = activityReports;
    }

    public List<CumulativeActivityReport> getActivityReports() {
        return activityReports;
    }

    public void setActivityReports(List<CumulativeActivityReport> activityReports) {
        this.activityReports = activityReports;
    }
}
