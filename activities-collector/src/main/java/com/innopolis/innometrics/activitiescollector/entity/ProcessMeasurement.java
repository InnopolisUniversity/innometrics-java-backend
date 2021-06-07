package com.innopolis.innometrics.activitiescollector.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "processmeasurements")
public class ProcessMeasurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Processid")
    private Process process;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "measurementtypeid")
//    private MeasurementType measurementType;

    @Column
    private Integer measurementtypeid;

    @Column
    private String value;

    @Column
    private String alternativelabel;

    @Column
    private Date captureddate;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @Column(name = "updateby", insertable = false)
    private String updateby;

    public ProcessMeasurement() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

//    public MeasurementType getMeasurementType() {
//        return measurementType;
//    }
//
//    public void setMeasurementType(MeasurementType measurementType) {
//        this.measurementType = measurementType;
//    }

    public Integer getMeasurementtypeid() {
        return measurementtypeid;
    }

    public void setMeasurementtypeid(Integer measurementtypeid) {
        this.measurementtypeid = measurementtypeid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAlternativelabel() {
        return alternativelabel;
    }

    public void setAlternativelabel(String alternativelabel) {
        this.alternativelabel = alternativelabel;
    }

    public Date getCaptureddate() {
        return captureddate;
    }

    public void setCaptureddate(Date captureddate) {
        this.captureddate = captureddate;
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
