package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class ConnectProjectRequest {
    private Integer AgentId;
    private String operation;
    private Integer projectID;
    private List<ParamsConfigDTO> params;

    public ConnectProjectRequest() {
        params = new ArrayList<>();
    }

    public Integer getAgentId() {
        return AgentId;
    }

    public void setAgentId(Integer agentId) {
        AgentId = agentId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public List<ParamsConfigDTO> getParams() {
        return params;
    }

    public void setParams(List<ParamsConfigDTO> params) {
        this.params = params;
    }
}
