package com.innopolis.innometrics.activitiescollector.DTO;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesReportByUserResponse {
    private List<ActivitiesReportByUser> Report;

    public ActivitiesReportByUserResponse() {
        Report = new ArrayList<>();
    }

    public ActivitiesReportByUserResponse(List<ActivitiesReportByUser> report) {
        Report = report;
    }

    public List<ActivitiesReportByUser> getReport() {
        return Report;
    }

    public void setReport(List<ActivitiesReportByUser> report) {
        Report = report;
    }
}
