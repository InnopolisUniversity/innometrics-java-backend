package com.innopolis.innometrics.authserver.DTO;

import java.util.Date;

public class CompanyRequest {

    private Integer companyid;

    private String companyname;

    private String isactive;

    private Date creationdate;

    private String createdby;

    private Date lastupdate;

    private String updateby;

    public CompanyRequest(){}

    public CompanyRequest(Integer companyid, String companyname, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby) {
        this.companyid = companyid;
        this.companyname = companyname;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
    }


    public Integer getCompanyid() {
        return companyid;
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

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}
