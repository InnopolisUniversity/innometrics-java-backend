package com.innopolis.innometrics.activitiescollector.entity;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

@Entity
@Table(name = "cumlativerepactivity")
@TypeDef(typeClass = PostgreSQLIntervalType.class, defaultForType = Duration.class)
public class CumulativeReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer reportid;//           INT GENERATED ALWAYS AS IDENTITY,

    @Column(name = "email")
    private String email;//              VARCHAR NOT NULL,

    @Column(name = "used_time", columnDefinition = "interval")
    private String used_time;//          interval  NOT NULL,
    @Column(name = "dailysum", columnDefinition = "interval")
    private String dailySum;//           interval  NOT NULL,
    @Column(name = "monthlysum", columnDefinition = "interval")
    private String monthlySum;//         interval NOT NULL,
    @Column(name = "yearlysum", columnDefinition = "interval")
    private String yearlySum;//          interval NOT NULL,

    @Column(name = "captureddate")
    private Date capturedDate;//       date not null,

    @Column(name = "executable_name")
    private String executable_name;//    VARCHAR NOT NULL,

    /*
    @Column(name = "creationdate")
    private Date creationdate;//       DATE NOT NULL default CURRENT_TIMESTAMP,

    @Column(name = "createdby")
    private String createdby;//          VARCHAR (25) NOT NULL default user,

    @Column(name = "lastupdate")
    private Date lastupdate;//         DATE,

    @Column(name = "updateby")
    private String updateby;//           VARCHAR (25)
*/

    public CumulativeReport() {
    }

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
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

    public Date getCapturedDate() {
        return capturedDate;
    }

    public void setCapturedDate(Date capturedDate) {
        this.capturedDate = capturedDate;
    }

    public String getExecutable_name() {
        return executable_name;
    }

    public void setExecutable_name(String executable_name) {
        this.executable_name = executable_name;
    }
/*
    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }
    */
}
