package com.innopolis.innometrics.activitiescollector.service;

import com.innopolis.innometrics.activitiescollector.DTO.*;
import com.innopolis.innometrics.activitiescollector.entity.*;
import com.innopolis.innometrics.activitiescollector.repository.ActivityRepository;
import com.innopolis.innometrics.activitiescollector.repository.CumulativeReportRepository;
import com.innopolis.innometrics.activitiescollector.repository.MeasurementRepository;
import com.innopolis.innometrics.activitiescollector.repository.MeasurementTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("ActivityService")
@Transactional
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    MeasurementTypeRepository measurementTypeRepository;

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    CumulativeReportRepository cumulativeReportRepository;


    public boolean CreateActivty(ActivityReport activityReport, String UserName, Date CreationDate) {
        Activity myActivity = new Activity();
        myActivity.setActivityID(activityReport.getActivityID());
        myActivity.setActivitytype(activityReport.getActivityType());
        myActivity.setIdle_activity(activityReport.getIdle_activity());
        myActivity.setEmail(activityReport.getUserID());
        myActivity.setStarttime(activityReport.getStart_time());
        myActivity.setEnd_time(activityReport.getEnd_time());
        myActivity.setExecutable_name(activityReport.getExecutable_name());
        myActivity.setBrowser_url(activityReport.getBrowser_url());
        myActivity.setBrowser_title(activityReport.getBrowser_title());
        myActivity.setIp_address(activityReport.getIp_address());
        myActivity.setMac_address(activityReport.getMac_address());
        myActivity.setPid(activityReport.getPid());
        myActivity.setOsversion(activityReport.getOsversion());

        myActivity.setCreationdate(CreationDate);
        myActivity.setCreatedby(UserName);

        myActivity = activityRepository.save(myActivity);

        for (MeasurementReport m : activityReport.getMeasurements()) {
            Measurement myMeasurement = new Measurement();
            myMeasurement.setActivity(myActivity);
            MeasurementType myType = new MeasurementType();
            try {
                myType = measurementTypeRepository.findByMeasurementtypeid(Integer.parseInt(m.getMeasurementTypeId()));
            } catch (Exception e) {
                myType = measurementTypeRepository.findByMeasurementtypeid(1);
            }

            if (myType == null) {
                //throw new ValidationException("The measurement type select does not exist");
            }
            myMeasurement.setMeasurementType(myType);
            myMeasurement.setValue(m.getValue());
            myMeasurement.setCreationdate(CreationDate);
            myMeasurement.setCreatedby(UserName);

            myMeasurement = measurementRepository.save(myMeasurement);


            myActivity.getMeasurements().add(myMeasurement);
        }

        //activityRepository.save(myActivity);

        return true;
    }

    public boolean DeleteActivity(Integer ActivityID, String UserName) {
        if (ActivityID == null || UserName == null) {
            //throw new ValidationException("Not enough data provided");
        }

        if (!activityRepository.existsById(ActivityID)) {
            //throw new ValidationException("The activity doesn't exist");
        }

        activityRepository.deleteById(ActivityID);

        return true;
    }

    public boolean DeleteActivitiesWithIds(Integer[] ids) {
        activityRepository.deleteActivitiesWithIds(Arrays.asList(ids));
        return true;
    }

    public ActivitiesReportResponse getActivitiesByEmailandDay(String UserName, Date reportDate) {
        List<IActivitiesReportByUserAndDay> myActivities;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        myActivities = activityRepository.getActivitiesPerDay(UserName, formatter.format(reportDate));

        ActivitiesReportResponse respose = new ActivitiesReportResponse();
        for (IActivitiesReportByUserAndDay activityReport : myActivities) {
            Activity myActivity = new Activity();
            myActivity.setActivityID(activityReport.getActivityID());
            myActivity.setActivitytype(activityReport.getActivitytype());
            myActivity.setIdle_activity(activityReport.getIdle_activity());
            myActivity.setEmail(activityReport.getEmail());
            myActivity.setStarttime(activityReport.getStarttime());
            myActivity.setEnd_time(activityReport.getEnd_time());
            myActivity.setExecutable_name(activityReport.getExecutable_name());
            myActivity.setBrowser_url(activityReport.getBrowser_url());
            myActivity.setBrowser_title(activityReport.getBrowser_title());
            myActivity.setIp_address(activityReport.getIp_address());
            myActivity.setMac_address(activityReport.getMac_address());
            myActivity.setPid(activityReport.getPid());
            myActivity.setOsversion(activityReport.getOsversion());
            myActivity.setCreationdate(activityReport.getCreationdate());
            myActivity.setCreatedby(activityReport.getCreatedby());

            respose.getReport().add(myActivity);
        }

        return respose;
    }

    public ActivitiesReportByUserResponse getActivitiesReportByUser(ActivitiesReportByUserRequest request) {
        List<IActivitiesReportByUser> result = activityRepository.getActivitiesReport(request.getProjectID(), request.getEmail(), request.getMin_Date(), request.getMax_Date());

        ActivitiesReportByUserResponse response = new ActivitiesReportByUserResponse();
        for (IActivitiesReportByUser a : result) {
            ActivitiesReportByUser temp = new ActivitiesReportByUser();
            if (a.getActivity_day() != null)
                temp.setActivity_day(a.getActivity_day());
            temp.setEmail(a.getEmail());
            temp.setExecutable_name(a.getExecutable_name());
            if (a.getTime_used() != null)
                temp.setTime_used(a.getTime_used());
            response.getReport().add(temp);
        }
        //response.getReport().addAll(result);

        return response;
    }


    public TimeReportResponse getTimeReportByUser(TimeReportRequest request) {
        List<ITimeReportByUser> result = activityRepository.getTimeReport(request.getProjectID(), request.getEmail(), request.getMin_Date(), request.getMax_Date());

        TimeReportResponse response = new TimeReportResponse();
        for (ITimeReportByUser a : result) {
            TimeReportByUser temp = new TimeReportByUser();
            if (a.getActivity_day() != null)
                temp.setActivity_day(a.getActivity_day());
            temp.setEmail(a.getEmail());
            if (a.getTime_used() != null)
                temp.setTime_used(a.getTime_used());
            response.getReport().add(temp);
        }
        //response.getReport().addAll(result);

        return response;
    }


    public CumulativeReportResponse getCumulativeReportByEmail(String email, Date min_Date, Date max_Date) {
        List<CumulativeReport> myReport = cumulativeReportRepository.getCumulativeReport(email, min_Date, max_Date);

        CumulativeReportResponse response = new CumulativeReportResponse();

        for (CumulativeReport r : myReport) {
            CumulativeActivityReport myApp = new CumulativeActivityReport();
            myApp.setEmail(r.getEmail());
            myApp.setExecutable_name(r.getExecutable_name());
            myApp.setCapturedDate(r.getCapturedDate().toString());
            myApp.setDailySum(r.getDailySum().toString());
            myApp.setMonthlySum(r.getMonthlySum().toString());
            myApp.setYearlySum(r.getYearlySum().toString());
            myApp.setUsed_time(r.getUsed_time().toString());

            response.getActivityReports().add(myApp);
        }

        return response;
    }
}

