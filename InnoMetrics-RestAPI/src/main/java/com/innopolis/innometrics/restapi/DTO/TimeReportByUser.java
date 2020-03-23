package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;

public class TimeReportByUser implements Serializable {
    private String Email;
    private String Time_used;
    private String Activity_day;

    public TimeReportByUser() {
    }


    public TimeReportByUser(String email, String time_used, String activity_day) {
        Email = email;
        Time_used = time_used;
        Activity_day = activity_day;
    }

    public String getEmail() {
        return Email;
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

    public void setTime_used(String time_used) {
        Time_used = time_used;
    }

    public void setActivity_day(String activity_day) {
        Activity_day = activity_day;
    }
}
