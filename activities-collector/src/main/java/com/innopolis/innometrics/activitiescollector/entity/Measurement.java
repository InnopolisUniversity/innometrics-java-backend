package com.innopolis.innometrics.activitiescollector.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "measurements")
public class Measurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activityid", updatable = false)
    private Activity activity;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "measurementtypeid")
//    private MeasurementType measurementType;

    @Column
    private Integer measurementtypeid;

    @Column(nullable = true)
    private String value;

    @Column
    private String alternativelabel;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @Column(name = "updateby", insertable = false)
    private String updateby;

    public Measurement() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

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
