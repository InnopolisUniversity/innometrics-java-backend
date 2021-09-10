package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class MethodsListResponse {
    private List<MethodConfigDTO> methodsList;

    public MethodsListResponse() {
        methodsList = new ArrayList<>();
    }

    public List<MethodConfigDTO> getMethodsList() {
        return methodsList;
    }

    public void setMethodsList(List<MethodConfigDTO> methodsList) {
        this.methodsList = methodsList;
    }

    public void add(MethodConfigDTO methodConfigDTO) {
        this.methodsList.add(methodConfigDTO);
    }
}
