package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class ExternalProjectTeamListResponse {
    private List<ExternalProjectTeamDTO> externalProjectTeamList;

    public ExternalProjectTeamListResponse() {
        this.externalProjectTeamList = new ArrayList<>();
    }

    public List<ExternalProjectTeamDTO> getExternalProjectTeamList() {
        return externalProjectTeamList;
    }

    public void setExternalProjectTeamList(List<ExternalProjectTeamDTO> externalProjectTeamList) {
        this.externalProjectTeamList = externalProjectTeamList;
    }

    public void add(ExternalProjectTeamDTO externalProjectTeamDTO) {
        this.externalProjectTeamList.add(externalProjectTeamDTO);
    }
}
