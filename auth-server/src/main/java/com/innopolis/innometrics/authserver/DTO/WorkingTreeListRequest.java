package com.innopolis.innometrics.authserver.DTO;

import java.util.ArrayList;
import java.util.List;

public class WorkingTreeListRequest {
    List<WorkingTreeRequest> workingTreeRequests;

    public WorkingTreeListRequest(){
        this.workingTreeRequests=new ArrayList<>();
    }

    public WorkingTreeListRequest(List<WorkingTreeRequest> workingTreeRequests) {
        this.workingTreeRequests = workingTreeRequests;
    }

    public List<WorkingTreeRequest> getWorkingTreeRequests() {
        return workingTreeRequests;
    }

    public void setWorkingTreeRequests(List<WorkingTreeRequest> workingTreeRequests) {
        this.workingTreeRequests = workingTreeRequests;
    }

    public void addWorkingTreeRequest(WorkingTreeRequest workingTreeRequests){
        this.workingTreeRequests.add(workingTreeRequests);
    }
}
