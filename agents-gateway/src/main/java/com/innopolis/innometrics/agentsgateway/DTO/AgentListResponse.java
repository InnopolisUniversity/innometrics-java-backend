package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class AgentListResponse {
    private List<AgentResponse> AgentList;

    public AgentListResponse() {
        AgentList = new ArrayList<>();
    }

    public List<AgentResponse> getAgentList() {
        return AgentList;
    }

    public void setAgentList(List<AgentResponse> agentList) {
        AgentList = agentList;
    }

    public void add(AgentResponse agentResponse) {
        this.AgentList.add(agentResponse);
    }

    public boolean isEmpty() {
        return AgentList.isEmpty();
    }
}
