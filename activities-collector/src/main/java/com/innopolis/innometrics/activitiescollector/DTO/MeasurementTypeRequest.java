package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;

public class MeasurementTypeRequest implements Serializable {

    private String label;
    private String description;
    private Float weight;
    private Float scale;
    private String operation;

    public MeasurementTypeRequest() {
    }

    public MeasurementTypeRequest(String label, String description, Float weight, Float scale, String operation, String isActive) {
        this.label = label;
        this.description = description;
        this.weight = weight;
        this.scale = scale;
        this.operation = operation;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
