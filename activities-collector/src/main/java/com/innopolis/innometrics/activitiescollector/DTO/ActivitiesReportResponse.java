package com.innopolis.innometrics.activitiescollector.DTO;

import com.innopolis.innometrics.activitiescollector.entity.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesReportResponse {
    private List<Activity> Report;

    public ActivitiesReportResponse() {
        Report = new ArrayList<>();
    }

    public ActivitiesReportResponse(List<Activity> report) {
        Report = report;
    }

    public List<Activity> getReport() {
        return Report;
    }

    public void setReport(List<Activity> report) {
        Report = report;
    }
}
