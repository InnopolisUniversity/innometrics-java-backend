package com.innopolis.innometrics.activitiescollector.service;

import com.innopolis.innometrics.activitiescollector.DTO.ActivityReport;
import com.innopolis.innometrics.activitiescollector.DTO.MeasurementReport;
import com.innopolis.innometrics.activitiescollector.DTO.Report;
import com.innopolis.innometrics.activitiescollector.entity.Activity;
import com.innopolis.innometrics.activitiescollector.entity.Measurement;
import com.innopolis.innometrics.activitiescollector.entity.MeasurementType;
//import com.innopolis.innometrics.activitiescollector.exceptions.ValidationException;
import com.innopolis.innometrics.activitiescollector.repository.ActivityRepository;
import com.innopolis.innometrics.activitiescollector.repository.MeasurementRepository;
import com.innopolis.innometrics.activitiescollector.repository.MeasurementTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public boolean CreateActivty(ActivityReport activityReport, String UserName, Date CreationDate) {
        Activity myActivity = new Activity();
        myActivity.setActivityID(activityReport.getActivityID());
        myActivity.setActivitytype(activityReport.getActivityType());
        myActivity.setIdle_activity(activityReport.getIdle_activity());
        myActivity.setEmail(activityReport.getUserID());
        myActivity.setStart_time(activityReport.getStart_time());
        myActivity.setEnd_time(activityReport.getEnd_time());
        myActivity.setExecutable_name(activityReport.getExecutable_name());
        myActivity.setBrowser_url(activityReport.getBrowser_url());
        myActivity.setBrowser_title(activityReport.getBrowser_title());
        myActivity.setIp_address(activityReport.getIp_address());
        myActivity.setMac_address(activityReport.getMac_address());

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

    public Report getActivitiesByEmail(String UserName) {
        List<Activity> myActivities = activityRepository.findByEmail(UserName);

        Report response = new Report();

        for (Activity a : myActivities) {
            ActivityReport myApp = new ActivityReport();
            myApp.setActivityID(a.getActivityID());
            myApp.setActivityType(a.getActivitytype());
            myApp.setIdle_activity(a.getIdle_activity());
            myApp.setUserID(a.getEmail());
            myApp.setStart_time(a.getStart_time());
            myApp.setEnd_time(a.getEnd_time());
            myApp.setExecutable_name(a.getExecutable_name());
            myApp.setBrowser_url(a.getBrowser_url());
            myApp.setBrowser_title(a.getBrowser_title());
            myApp.setIp_address(a.getIp_address());
            myApp.setMac_address(a.getMac_address());

            for (Measurement m : a.getMeasurements()) {
                MeasurementReport myMeasure = new MeasurementReport();
                myMeasure.setMeasurementTypeId(m.getMeasurementType().getMeasurementtypeid().toString());
                myMeasure.setValue(m.getValue());
                myMeasure.setAlternativeLabel(m.getMeasurementType().getLabel());
                myApp.getMeasurements().add(myMeasure);
            }

            response.getActivities().add(myApp);

        }

        return response;
    }

}
