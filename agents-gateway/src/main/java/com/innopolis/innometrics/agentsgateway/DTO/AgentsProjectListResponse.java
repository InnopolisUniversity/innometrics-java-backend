package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class AgentsProjectListResponse {
    private List<AgentsProjectDTO> agentsProjectList;

    public AgentsProjectListResponse() {
        this.agentsProjectList = new ArrayList<>();
    }

    public List<AgentsProjectDTO> getAgentsProjectList() {
        return agentsProjectList;
    }

    public void setAgentsProjectList(List<AgentsProjectDTO> agentsProjectList) {
        this.agentsProjectList = agentsProjectList;
    }

    public void add(AgentsProjectDTO agentsProjectDTO) {
        this.agentsProjectList.add(agentsProjectDTO);
    }
}
