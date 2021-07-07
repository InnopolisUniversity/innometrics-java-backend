package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class AgentsCompanyListResponse {
    private List<AgentsCompanyDTO> agentsCompanyList;

    public AgentsCompanyListResponse() {
        this.agentsCompanyList = new ArrayList<>();
    }

    public List<AgentsCompanyDTO> getAgentsCompanyList() {
        return agentsCompanyList;
    }

    public void setAgentsCompanyList(List<AgentsCompanyDTO> agentsCompanyList) {
        this.agentsCompanyList = agentsCompanyList;
    }

    public void add(AgentsCompanyDTO agentsCompanyDTO) {
        this.agentsCompanyList.add(agentsCompanyDTO);
    }
}
