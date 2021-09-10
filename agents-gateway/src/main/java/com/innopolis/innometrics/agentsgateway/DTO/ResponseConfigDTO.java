package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;
import java.util.Date;

public class ResponseConfigDTO implements Serializable {
    private Integer configresponseid;
    private Integer methodid;
    private String responseparam;
    private String paramname;
    private String paramtype;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;

    public ResponseConfigDTO() {
    }

    public ResponseConfigDTO(Integer configresponseid, Integer methodid, String responseparam, String paramname,
                             String paramtype, String isactive, Date creationdate, String createdby, Date lastupdate,
                             String updateby) {
        this.configresponseid = configresponseid;
        this.methodid = methodid;
        this.responseparam = responseparam;
        this.paramname = paramname;
        this.paramtype = paramtype;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
    }

    public Integer getConfigresponseid() {
        return configresponseid;
    }

    public void setConfigresponseid(Integer configresponseid) {
        this.configresponseid = configresponseid;
    }

    public Integer getMethodid() {
        return methodid;
    }

    public void setMethodid(Integer methodid) {
        this.methodid = methodid;
    }

    public String getResponseparam() {
        return responseparam;
    }

    public void setResponseparam(String responseparam) {
        this.responseparam = responseparam;
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

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
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
