package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;

public class MeasurementReport implements Serializable {
    private String measurementTypeId;
    private String value;
    private String alternativeLabel;

    public MeasurementReport() {
    }

    public MeasurementReport(String measurementTypeId, String value, String alternativeLabel) {
        this.measurementTypeId = measurementTypeId;
        this.value = value;
        this.alternativeLabel = alternativeLabel;
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
}
