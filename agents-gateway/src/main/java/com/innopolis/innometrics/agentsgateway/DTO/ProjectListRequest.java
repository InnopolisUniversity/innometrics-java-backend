package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectListRequest implements Serializable {
    private Integer AgentId;
    private String operation;
    private List<ParamsConfigDTO> params;

    public ProjectListRequest() {
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

    public List<ParamsConfigDTO> getParams() {
        return params;
    }

    public void setParams(List<ParamsConfigDTO> params) {
        this.params = params;
    }
}
