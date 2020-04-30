package com.innopolis.innometrics.agentsgateway.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepoEventDTO implements Serializable {
    private String eventdate;
    private String eventauthor;
    private String eventdescription;

    public RepoEventDTO() {
    }

    public RepoEventDTO(String eventdate, String eventauthor, String eventdescription) {
        this.eventdate = eventdate;
        this.eventauthor = eventauthor;
        this.eventdescription = eventdescription;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getEventauthor() {
        return eventauthor;
    }

    public void setEventauthor(String eventauthor) {
        this.eventauthor = eventauthor;
    }

    public String getEventdescription() {
        return eventdescription;
    }

    public void setEventdescription(String eventdescription) {
        this.eventdescription = eventdescription;
    }
}
