package com.innopolis.innometrics.authserver.DTO;

import java.util.ArrayList;
import java.util.List;

public class TeamListRequest {
    List<TeamRequest> teamRequestList;

    public TeamListRequest() {
        teamRequestList = new ArrayList<>();
    }

    public TeamListRequest(List<TeamRequest> teamRequestList) {
        this.teamRequestList = teamRequestList;
    }

    public List<TeamRequest> getTeamRequestList() {
        return teamRequestList;
    }

    public void setTeamRequestList(List<TeamRequest> teamRequestList) { this.teamRequestList = teamRequestList; }

    public void addTeamRequest(TeamRequest teamRequestList){
        this.teamRequestList.add(teamRequestList);
    }
}
