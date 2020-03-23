package com.innopolis.innometrics.activitiescollector.DTO;

import java.util.ArrayList;
import java.util.List;

public class TimeReportResponse {
    private List<TimeReportByUser> Report;

    public TimeReportResponse() {
        Report = new ArrayList<>();
    }

    public TimeReportResponse(List<TimeReportByUser> report) {
        Report = report;
    }

    public List<TimeReportByUser> getReport() {
        return Report;
    }

    public void setReport(List<TimeReportByUser> report) {
        Report = report;
    }
}
