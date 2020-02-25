package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Report implements Serializable {
    private Set<ActivityReport> activities = new HashSet<>();

    public Report() {
    }

    public Report(Set<ActivityReport> activities) {
        this.activities = activities;
    }

    public Set<ActivityReport> getActivities() {
        return activities;
    }

    public void setActivities(Set<ActivityReport> activities) {
        this.activities = activities;
    }
}
