package com.innopolis.innometrics.activitiescollector.DTO;

import java.util.ArrayList;
import java.util.List;

public class BugTrackingListRequest {
    List<BugTrackingRequest> bugTrackingRequests;

    public BugTrackingListRequest() {
        bugTrackingRequests = new ArrayList<>();
    }

    public BugTrackingListRequest(List<BugTrackingRequest> bugTrackingRequests) {
        this.bugTrackingRequests = bugTrackingRequests;
    }

    public List<BugTrackingRequest> getBugTrackingRequests() {
        return bugTrackingRequests;
    }

    public void setBugTrackingRequests(List<BugTrackingRequest> bugTrackingRequests) {
        this.bugTrackingRequests = bugTrackingRequests;
    }

    public void addBugTrackingRequest(BugTrackingRequest bugTrackingRequest){
        this.bugTrackingRequests.add(bugTrackingRequest);
    }
}
