package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class AgentConfigResponse {
    private List<MethodConfigDTO> methodsConfig;

    public AgentConfigResponse() {
        methodsConfig = new ArrayList<>();
    }

    public List<MethodConfigDTO> getMethodsConfig() {
        return methodsConfig;
    }

    public void setMethodsConfig(List<MethodConfigDTO> methodsConfig) {
        this.methodsConfig = methodsConfig;
    }

    public void addMethodConfig(MethodConfigDTO methodConfig) {
        methodsConfig.add(methodConfig);
    }
}
