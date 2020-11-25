package com.innopolis.innometrics.activitiescollector.DTO;

import com.innopolis.innometrics.activitiescollector.entity.Measurement;

import java.util.Date;

public interface IActivitiesReportByUserAndDay {
    public Integer getActivityID();

    public String getActivitytype();

    public Boolean getIdle_activity() ;
    public String getEmail();

    public Date getStarttime();

    public Date getEnd_time();

    public String getExecutable_name();

    public String getBrowser_url();

    public String getBrowser_title();

    public String getPid();

    public String getOsversion();

    public String getIp_address();

    public String getMac_address();

    public String getValue();

    public Date getCreationdate();

    public String getCreatedby();

    public Date getLastupdate();

    public String getUpdateby();
}
