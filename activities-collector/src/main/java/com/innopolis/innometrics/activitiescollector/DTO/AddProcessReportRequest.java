package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;
import java.util.List;

public class AddProcessReportRequest implements Serializable {
    private List<ProcessReport> ProcessesReport;

    public AddProcessReportRequest() {
    }

    public AddProcessReportRequest(List<ProcessReport> ProcessesReport) {
        this.ProcessesReport = ProcessesReport;
    }

    public List<ProcessReport> getProcessesReport() {
        return ProcessesReport;
    }

    public void setProcessesReport(List<ProcessReport> processesReport) {
        ProcessesReport = processesReport;
    }
}
