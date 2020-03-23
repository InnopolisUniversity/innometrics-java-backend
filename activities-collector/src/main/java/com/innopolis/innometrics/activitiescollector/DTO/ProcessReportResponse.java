package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProcessReportResponse implements Serializable {
    private List<ProcessReport> processReports;

    public ProcessReportResponse() {
        processReports = new ArrayList<>();
    }

    public ProcessReportResponse(List<ProcessReport> processReports) {
        this.processReports = processReports;
    }

    public List<ProcessReport> getProcessReports() {
        return processReports;
    }

    public void setProcessReports(List<ProcessReport> processReports) {
        this.processReports = processReports;
    }
}
