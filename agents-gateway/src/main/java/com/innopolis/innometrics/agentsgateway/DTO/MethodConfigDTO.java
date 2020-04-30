package com.innopolis.innometrics.agentsgateway.DTO;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MethodConfigDTO implements Serializable {
    private Integer methodid;
    private String description;
    private String operation;
    private List<ParamsConfigDTO> parameters;

    public MethodConfigDTO() {
        parameters = new ArrayList<>();
    }

    public MethodConfigDTO(Integer methodid, String description, String operation, List<ParamsConfigDTO> parameters) {
        this.methodid = methodid;
        this.description = description;
        this.operation = operation;
        this.parameters = parameters;
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

    public List<ParamsConfigDTO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParamsConfigDTO> parameters) {
        this.parameters = parameters;
    }
}
