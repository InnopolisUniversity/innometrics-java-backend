package com.innopolis.innometrics.activitiescollector.DTO;

import java.util.ArrayList;
import java.util.List;

public class CategoriesTimeReportResponse {
    private List<CategoriesReport> Report;

    public CategoriesTimeReportResponse() {
        Report = new ArrayList<>();
    }

    public CategoriesTimeReportResponse(List<CategoriesReport> report) {
        Report = report;
    }

    public List<CategoriesReport> getReport() {
        return Report;
    }

    public void setReport(List<CategoriesReport> report) {
        Report = report;
    }
}
