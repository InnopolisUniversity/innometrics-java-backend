package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;

public class MeasurementReport implements Serializable {
    private Integer measurementTypeId;
    private Float value;

    public MeasurementReport() {
    }

    public MeasurementReport(Integer measurementTypeId, Float value) {
        this.measurementTypeId = measurementTypeId;
        this.value = value;
    }

    public Integer getMeasurementTypeId() {
        return measurementTypeId;
    }

    public void setMeasurementTypeId(Integer measurementTypeId) {
        this.measurementTypeId = measurementTypeId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
