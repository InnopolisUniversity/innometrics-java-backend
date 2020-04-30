package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;

public class AppCategoryRequest implements Serializable {
    private Integer appid;
    private String appname;
    private Integer categoryid;
    private String isactive;

    public AppCategoryRequest() {
    }

    public AppCategoryRequest(Integer appid, String appname, Integer categoryid, String isactive) {
        this.appid = appid;
        this.appname = appname;
        this.categoryid = categoryid;
        this.isactive = isactive;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}
