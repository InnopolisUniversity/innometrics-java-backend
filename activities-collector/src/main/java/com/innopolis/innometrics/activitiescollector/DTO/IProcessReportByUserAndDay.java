package com.innopolis.innometrics.activitiescollector.DTO;

import java.util.Date;

public interface IProcessReportByUserAndDay {
    public Integer getProcessID();

    public String getEmail();

    public Integer getProjectid();

    public String getExecutable_name();

    public String getPid();

    public Date getCollectedtime();

    public String getOsversion() ;

    public String getIp_address();

    public String getMac_address();

    public Date getCreationdate();

    public String getCreatedby();

    public Date getLastupdate();

    public String getUpdateby();
}
