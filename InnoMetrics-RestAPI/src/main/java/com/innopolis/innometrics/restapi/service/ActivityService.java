package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.ActivityReport;
import com.innopolis.innometrics.restapi.DTO.MeasurementReport;
import com.innopolis.innometrics.restapi.DTO.Report;
import com.innopolis.innometrics.restapi.DTO.UserRequest;
import com.innopolis.innometrics.restapi.entitiy.Activity;
import com.innopolis.innometrics.restapi.entitiy.Measurement;
import com.innopolis.innometrics.restapi.entitiy.MeasurementType;
import com.innopolis.innometrics.restapi.exceptions.ValidationException;
import com.innopolis.innometrics.restapi.repository.ActivityRepository;
import com.innopolis.innometrics.restapi.repository.MeasurementRepository;
import com.innopolis.innometrics.restapi.repository.MeasurementTypeRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    MeasurementTypeRepository measurementTypeRepository;

    @Autowired
    MeasurementRepository measurementRepository;

    private String baseURL = "http://INNOMETRICS-COLLECTOR-SERVER/V1/activity";


    @HystrixCommand( commandKey = "CreateActivity", fallbackMethod = "CreateActivityFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public boolean CreateActivity(Report report, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<Report> entity = new HttpEntity<>(report, headers);
        try {
            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);

            HttpStatus status = response.getStatusCode();

            return status == HttpStatus.OK;
        } catch (Exception e) {
            LOG.warn(e);
            return false;
        }

    }

    public boolean CreateActivityFallback(Report report, String token, Throwable exception) {
        LOG.warn("CreateActivityFallback method used");
        LOG.warn(exception);
        return false;
    }
/*
    public boolean CreateActivity(ActivityReport activityReport, String UserName, Date CreationDate) {
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
    }*/

    public boolean DeleteActivity(Integer ActivityID, String UserName) {
        if (ActivityID == null || UserName == null) {
            throw new ValidationException("Not enough data provided");
        }

        if (!activityRepository.existsById(ActivityID)) {
            throw new ValidationException("The activity doesn't exist");
        }

        activityRepository.deleteById(ActivityID);

        return true;
    }

    @HystrixCommand(commandKey = "getActivitiesByEmail", fallbackMethod = "getActivitiesByEmailFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public Report getActivitiesByEmail(String userName, String token) {
        String uri = baseURL + "/" + userName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        Map<String, String> vars = new HashMap<>();
        vars.put("email", userName);

        HttpEntity<Report> entity = new HttpEntity<>(headers);
        ResponseEntity<Report> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Report.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public Report getActivitiesByEmailFallback(String userName, String token, Throwable exception) {
        LOG.warn("getActivitiesByEmailFallback method used");
        LOG.warn(exception);
        Report myReport = new Report();
        ActivityReport myDefaultActivity = new ActivityReport();
        myDefaultActivity.setExecutable_name("Not info available");
        myDefaultActivity.setUserID("Not info available");
        myDefaultActivity.setIp_address("Not info available");
        myDefaultActivity.setMac_address("Not info available");
        myDefaultActivity.setBrowser_title("Not info available");
        myDefaultActivity.setBrowser_url("Not info available");
        myDefaultActivity.setActivityType("Not info available");
        myReport.getActivities().add(myDefaultActivity);
        return myReport;
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
                //myApp.getMeasurements().add(myMeasure);
            }

            response.getActivities().add(myApp);

        }

        return response;
    }

}
