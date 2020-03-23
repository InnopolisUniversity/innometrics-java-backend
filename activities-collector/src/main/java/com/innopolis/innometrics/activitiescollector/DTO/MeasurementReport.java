package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;
import java.util.Date;

public class MeasurementReport implements Serializable {
    private String measurementTypeId;
    private String value;
    private String alternativeLabel;
    private Date capturedDate;

    public MeasurementReport() {
    }

    public MeasurementReport(String measurementTypeId, String value, String alternativeLabel, Date capturedDate) {
        this.measurementTypeId = measurementTypeId;
        this.value = value;
        this.alternativeLabel = alternativeLabel;
        this.capturedDate = capturedDate;
    }

    public String getMeasurementTypeId() {
        return measurementTypeId;
    }

    public void setMeasurementTypeId(String measurementTypeId) {
        this.measurementTypeId = measurementTypeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAlternativeLabel() {
        return alternativeLabel;
    }

    public void setAlternativeLabel(String alternativeLabel) {
        this.alternativeLabel = alternativeLabel;
    }

    public Date getCapturedDate() {
        return capturedDate;
    }

    public void setCapturedDate(Date capturedDate) {
        this.capturedDate = capturedDate;
    }
}
