package com.innopolis.innometrics.activitiescollector.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "measurementtypes")
public class MeasurementType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurementtypeid", updatable = false)
    private Integer measurementtypeid;

    @Column
    private String label;

    @Column
    private String description;

    @Column
    private Float weight;

    @Column
    private Float scale;

    @Column
    private String operation;

    @Column
    private String isactive;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @Column(name = "updateby", insertable = false)
    private String updateby;

    public MeasurementType() {
    }

    public MeasurementType(Integer measurementtypeid, String label, String description, Float weight, Float scale, String operation, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby) {
        measurementtypeid = measurementtypeid;
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
        measurementtypeid = measurementtypeid;
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

    public void setIsactive(String isActive) {
        this.isactive = isActive;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
