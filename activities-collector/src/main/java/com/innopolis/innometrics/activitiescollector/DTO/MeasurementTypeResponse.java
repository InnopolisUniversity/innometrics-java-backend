package com.innopolis.innometrics.activitiescollector.DTO;

import com.innopolis.innometrics.activitiescollector.entity.MeasurementType;

import java.io.Serializable;
import java.util.Date;

public class MeasurementTypeResponse implements Serializable {

    private Integer measurementtypeid;
    private String label;
    private String description;
    private Float weight;
    private Float scale;
    private String operation;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;

    public MeasurementTypeResponse() {
    }

    public MeasurementTypeResponse(MeasurementType measurementType) {
        this.measurementtypeid = measurementType.getMeasurementtypeid();
        this.label = measurementType.getLabel();
        this.description = measurementType.getDescription();
        this.weight = measurementType.getWeight();
        this.scale = measurementType.getScale();
        this.operation = measurementType.getOperation();
        this.isactive = measurementType.getIsactive();
        this.creationdate = measurementType.getCreationdate();
        this.createdby = measurementType.getCreatedby();
        this.lastupdate = measurementType.getLastupdate();
        this.updateby = measurementType.getUpdateby();
    }

    public MeasurementTypeResponse(Integer measurementtypeid, String label, String description, Float weight, Float scale, String operation, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby) {
        this.measurementtypeid = measurementtypeid;
        this.label = label;
        this.description = description;
        this.weight = weight;
        this.scale = scale;
        this.operation = operation;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
    }

    public Integer getMeasurementtypeid() {
        return measurementtypeid;
    }

    public void setMeasurementtypeid(Integer measurementtypeid) {
        this.measurementtypeid = measurementtypeid;
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

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }
}
