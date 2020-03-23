package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivitiesReportByUserResponse implements Serializable {
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
