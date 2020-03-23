package com.innopolis.innometrics.activitiescollector.DTO;

public class CumulativeActivityReport {

    private String email;
    private String used_time;
    private String dailySum;
    private String monthlySum;
    private String yearlySum;
    private String capturedDate;
    private String executable_name;

    public CumulativeActivityReport() {
    }

    public CumulativeActivityReport(String email, String used_time, String dailySum, String monthlySum, String yearlySum, String capturedDate, String executable_name) {
        this.email = email;
        this.used_time = used_time;
        this.dailySum = dailySum;
        this.monthlySum = monthlySum;
        this.yearlySum = yearlySum;
        this.capturedDate = capturedDate;
        this.executable_name = executable_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsed_time() {
        return used_time;
    }

    public void setUsed_time(String used_time) {
        this.used_time = used_time;
    }

    public String getDailySum() {
        return dailySum;
    }

    public void setDailySum(String dailySum) {
        this.dailySum = dailySum;
    }

    public String getMonthlySum() {
        return monthlySum;
    }

    public void setMonthlySum(String monthlySum) {
        this.monthlySum = monthlySum;
    }

    public String getYearlySum() {
        return yearlySum;
    }

    public void setYearlySum(String yearlySum) {
        this.yearlySum = yearlySum;
    }

    public String getCapturedDate() {
        return capturedDate;
    }

    public void setCapturedDate(String capturedDate) {
        this.capturedDate = capturedDate;
    }

    public String getExecutable_name() {
        return executable_name;
    }

    public void setExecutable_name(String executable_name) {
        this.executable_name = executable_name;
    }
}
