package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;

public class ActivitiesReportByUser implements Serializable {
    private static final long serialVersionUID = 3801383401971413246L;
    private String Email;
    private String Executable_name;
    private String Time_used;
    private String Activity_day;

    public ActivitiesReportByUser() {
    }

    public ActivitiesReportByUser(String email, String executable_name, String time_used, String activity_day) {
        Email = email;
        Executable_name = executable_name;
        Time_used = time_used;
        Activity_day = activity_day;
    }

    public String getEmail() {
        return Email;
    }

    public String getExecutable_name() {
        return Executable_name;
    }

    public String getTime_used() {
        return Time_used;
    }

    public String getActivity_day() {
        return Activity_day;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setExecutable_name(String executable_name) {
        Executable_name = executable_name;
    }

    public void setTime_used(String time_used) {
        Time_used = time_used;
    }

    public void setActivity_day(String activity_day) {
        Activity_day = activity_day;
    }
}
