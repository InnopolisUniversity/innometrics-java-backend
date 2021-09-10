package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.Dictionary;
import java.util.Hashtable;

public class MetricsResponse {
    Hashtable<String, String> metricList;

    public MetricsResponse() {
        metricList = new Hashtable<>();
    }

    public MetricsResponse(Hashtable<String, String> metricList) {
        this.metricList = metricList;
    }

    public Hashtable<String, String> getMetricList() {
        return metricList;
    }

    public void setMetricList(Hashtable<String, String> metricList) {
        this.metricList = metricList;
    }
}
