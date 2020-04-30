package com.innopolis.innometrics.activitiescollector.DTO;


import java.io.Serializable;

public class CategoryRequest implements Serializable {
    private Integer categoryid;
    private String categoryname;
    private String isactive;

    public CategoryRequest() {
    }

    public CategoryRequest(Integer categoryid, String categoryname, String isactive) {
        this.categoryid = categoryid;
        this.categoryname = categoryname;
        this.isactive = isactive;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}
