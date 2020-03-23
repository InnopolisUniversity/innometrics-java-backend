package com.innopolis.innometrics.configservice.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cl_apps_categories")
public class App_category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appid", updatable = false)
    private Integer appid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catid")
    private Category category;

    @Column
    private String appname;

    @Column
    private String appdescription;

    @Column
    private String executablefile;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @Column(name = "updateby", insertable = false)
    private String updateby;

    public App_category() {
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getApp_name() {
        return appname;
    }

    public void setApp_name(String app_name) {
        this.appname = app_name;
    }

    public String getApp_description() {
        return appdescription;
    }

    public void setApp_description(String app_description) {
        this.appdescription = app_description;
    }

    public String getExecutable_file() {
        return executablefile;
    }

    public void setExecutable_file(String executable_file) {
        this.executablefile = executable_file;
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
