package com.innopolis.innometrics.agentsgateway.DTO;

public class AgentStatus implements IAgentStatus {
    private String Agentid;
    private String Agentname;
    private String Description;
    private String Isconnected;

    public AgentStatus() {
    }

    public AgentStatus(String agentid, String agentname, String description, String isconnected) {
        Agentid = agentid;
        Agentname = agentname;
        Description = description;
        Isconnected = isconnected;
    }

    @Override
    public String getAgentid() {
        return Agentid;
    }

    public void setAgentid(String agentid) {
        Agentid = agentid;
    }

    @Override
    public String getAgentname() {
        return Agentname;
    }

    public void setAgentname(String agentname) {
        Agentname = agentname;
    }

    @Override
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String getIsconnected() {
        return Isconnected;
    }

    public void setIsconnected(String isconnected) {
        Isconnected = isconnected;
    }
}
