package com.innopolis.innometrics.configservice.DTO;

import java.io.Serializable;
import java.util.Date;

public class CategoryResponse  implements Serializable {
    private Integer catid;
    private String cat_name;
    private String cat_description;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;

    public CategoryResponse() {
    }

    public CategoryResponse(Integer catid, String cat_name, String cat_description, Date creationdate, String createdby, Date lastupdate, String updateby) {
        this.catid = catid;
        this.cat_name = cat_name;
        this.cat_description = cat_description;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
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
