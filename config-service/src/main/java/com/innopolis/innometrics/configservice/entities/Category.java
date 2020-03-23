package com.innopolis.innometrics.configservice.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cl_categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "catid", updatable = false)
    private Integer catid;

    @Column(name= "catname")
    private String catname;

    @Column
    private String catdescription;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @Column(name = "updateby", insertable = false)
    private String updateby;

    @OneToMany
    @JoinColumn(name = "catid")
    private Set<App_category> measurements = new HashSet<App_category>();

    public Category() {
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getCat_name() {
        return catname;
    }

    public void setCat_name(String cat_name) {
        this.catname = cat_name;
    }

    public String getCat_description() {
        return catdescription;
    }

    public void setCat_description(String cat_description) {
        this.catdescription = cat_description;
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

    public Set<App_category> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Set<App_category> measurements) {
        this.measurements = measurements;
    }
}
