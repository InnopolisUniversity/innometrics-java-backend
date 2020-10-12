package com.innopolis.innometrics.authserver.DTO;

import java.util.ArrayList;
import java.util.List;

public class TeammembersListRequest {
    List<TeammembersRequest> teammembersRequestList;

    public TeammembersListRequest() {
        teammembersRequestList = new ArrayList<>();
    }

    public TeammembersListRequest(List<TeammembersRequest> teammembersRequestList) {
        this.teammembersRequestList = teammembersRequestList;
    }

    public List<TeammembersRequest> getTeammembersRequestList() {
        return teammembersRequestList;
    }

    public void setTeammembersRequestList(List<TeammembersRequest> teammembersRequestList) { this.teammembersRequestList = teammembersRequestList; }

    public void addTeammembersRequest(TeammembersRequest teammembersRequest){
        this.teammembersRequestList.add(teammembersRequest);
    }
}
