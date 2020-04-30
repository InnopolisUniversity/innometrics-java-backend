package com.innopolis.innometrics.activitiescollector.DTO;

public class TimeReportByUser implements ITimeReportByUser {
    private String Email;
    private String Time_used;
    private String Activity_day;
    private String DateToSort;

    public TimeReportByUser() {
    }


    public TimeReportByUser(String email, String time_used, String activity_day) {
        Email = email;
        Time_used = time_used;
        Activity_day = activity_day;
    }

    @Override
    public String getEmail() {
        return Email;
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
