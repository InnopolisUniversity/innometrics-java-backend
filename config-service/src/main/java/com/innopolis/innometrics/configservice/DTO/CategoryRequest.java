package com.innopolis.innometrics.configservice.DTO;


import java.io.Serializable;

public class CategoryRequest  implements Serializable {
    private Integer catid;
    private String cat_name;
    private String cat_description;

    public CategoryRequest() {
    }

    public CategoryRequest(Integer catid, String cat_name, String cat_description) {
        this.catid = catid;
        this.cat_name = cat_name;
        this.cat_description = cat_description;
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_description() {
        return cat_description;
    }

    public void setCat_description(String cat_description) {
        this.cat_description = cat_description;
    }
}
