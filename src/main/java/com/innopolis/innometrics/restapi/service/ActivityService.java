package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.ActivityReport;
import com.innopolis.innometrics.restapi.DTO.MeasurementReport;
import com.innopolis.innometrics.restapi.entitiy.Activity;
import com.innopolis.innometrics.restapi.entitiy.Measurement;
import com.innopolis.innometrics.restapi.entitiy.MeasurementType;
import com.innopolis.innometrics.restapi.exceptions.ValidationException;
import com.innopolis.innometrics.restapi.repository.ActivityRepository;
import com.innopolis.innometrics.restapi.repository.MeasurementRepository;
import com.innopolis.innometrics.restapi.repository.MeasurementTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("ActivityService")
@Transactional
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    MeasurementTypeRepository measurementTypeRepository;

    @Autowired
    MeasurementRepository measurementRepository;

    public boolean CreateActivty(ActivityReport activityReport, String UserName, Date CreationDate){
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

            MeasurementType myType = measurementTypeRepository.findByMeasurementtypeid(Integer.parseInt(m.getMeasurementTypeId()));
            if(myType == null)
            {
                throw new ValidationException("The measurement type select does not exist");
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

    public boolean DeleteActivity(Integer ActivityID, String UserName){
        if(ActivityID == null || UserName == null){
            throw new ValidationException("Not enough data provided");
        }

        if(!activityRepository.existsById(ActivityID)){
            throw new ValidationException("The activity doesn't exist");
        }

        activityRepository.deleteById(ActivityID);

        return true;
    }
}
