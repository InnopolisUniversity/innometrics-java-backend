package com.innopolis.innometrics.authserver.DTO;

public class WorkingTreeRequest {

    private String email;

    private Integer memberid;

    private Integer teamid;

    private String teamname;

    private String teamdescription;

    private Integer companyid;

    private String companyname;

    private Integer projectID;

    private String projectname;

    public WorkingTreeRequest(String email, Integer memberid, Integer teamid, String teamname, String teamdescription, Integer companyid, String companyname, Integer projectID, String projectname) {
        this.email = email;
        this.memberid = memberid;
        this.teamid = teamid;
        this.teamname = teamname;
        this.teamdescription = teamdescription;
        this.companyid = companyid;
        this.companyname = companyname;
        this.projectID = projectID;
        this.projectname = projectname;
    }

    public WorkingTreeRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getTeamdescription() {
        return teamdescription;
    }

    public void setTeamdescription(String teamdescription) {
        this.teamdescription = teamdescription;
    }
}
