package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepoResponseDTO implements Serializable {
    private String agentname;
    private List<RepoEventDTO> events;

    public RepoResponseDTO() {
        events = new ArrayList<>();
    }

    public RepoResponseDTO(String agentname, List<RepoEventDTO> events) {
        this.agentname = agentname;
        this.events = events;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public List<RepoEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<RepoEventDTO> events) {
        this.events = events;
    }


}
