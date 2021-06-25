package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MethodConfigDTO implements Serializable {
    private Integer methodid;
    private String description;
    private String operation;
    private Integer agentid;
    private String endpoint;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
    private String requesttype;
    private List<ParamsConfigDTO> configParameters;
    //private List<ParamsResponseDTO> responseParameters;

    public MethodConfigDTO() {
        configParameters = new ArrayList<>();
    }

    public MethodConfigDTO(Integer methodid, String description, String operation, List<ParamsConfigDTO> configParameters) {
        this.methodid = methodid;
        this.description = description;
        this.operation = operation;
        this.configParameters = configParameters;
    }

    public Integer getMethodid() {
        return methodid;
    }

    public void setMethodid(Integer methodid) {
        this.methodid = methodid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<ParamsConfigDTO> getConfigParameters() {
        return configParameters;
    }

    public void setConfigParameters(List<ParamsConfigDTO> configParameters) {
        this.configParameters = configParameters;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }
}
