package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;
import java.util.Date;

public class DetailsConfigDTO implements Serializable {
    private Integer configDetId;
    private Integer methodid;
    private String paramname;
    private String paramtype;
    private String requestparam;
    private String requesttype;
    private String isactive;
    private String defaultvalue;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;

    public DetailsConfigDTO() {
    }

    public DetailsConfigDTO(Integer configDetId, Integer methodid, String paramname, String paramtype, String requestparam, String requesttype, String isactive, String defaultvalue, Date creationdate, String createdby, Date lastupdate, String updateby) {
        this.configDetId = configDetId;
        this.methodid = methodid;
        this.paramname = paramname;
        this.paramtype = paramtype;
        this.requestparam = requestparam;
        this.requesttype = requesttype;
        this.isactive = isactive;
        this.defaultvalue = defaultvalue;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
    }

    public Integer getConfigDetId() {
        return configDetId;
    }

    public void setConfigDetId(Integer configDetId) {
        this.configDetId = configDetId;
    }

    public Integer getMethodid() {
        return methodid;
    }

    public void setMethodid(Integer methodid) {
        this.methodid = methodid;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public String getParamtype() {
        return paramtype;
    }

    public void setParamtype(String paramtype) {
        this.paramtype = paramtype;
    }

    public String getRequestparam() {
        return requestparam;
    }

    public void setRequestparam(String requestparam) {
        this.requestparam = requestparam;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
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
