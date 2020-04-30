package com.innopolis.innometrics.activitiescollector.DTO;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class ActivitiesReportByUser implements IActivitiesReportByUser {
    private String Email;
    private String Executable_name;
    private String Time_used;
    private String Activity_day;
    private String DateToSort;

    public ActivitiesReportByUser() {
    }


    public ActivitiesReportByUser(String email, String executable_name, String time_used, String activity_day) {
        Email = email;
        Executable_name = executable_name;
        Time_used = time_used;
        Activity_day = activity_day;
    }

    @Override
    public String getEmail() {
        return Email;
    }

    @Override
    public String getExecutable_name() {
        return Executable_name;
    }

    @Override
    public String getTime_used() {
        return Time_used;
    }

    @Override
    public String getActivity_day() {
        return Activity_day;
    }

    @Override
    public String getDateToSort() {
        return DateToSort;
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

    public void setDateToSort(String dateToSort) {
        DateToSort = dateToSort;
    }
}
