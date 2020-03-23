package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.*;
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
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcessService {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    private String baseURL = "http://INNOMETRICS-COLLECTOR-SERVER/V1/process";


    @HystrixCommand(commandKey = "CreateProcessReport", fallbackMethod = "CreateProcessReportFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public boolean CreateProcessReport(AddProcessReportRequest report, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<AddProcessReportRequest> entity = new HttpEntity<>(report, headers);
        ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);

        HttpStatus status = response.getStatusCode();

        return status == HttpStatus.OK;

    }

    public boolean CreateProcessReportFallback(AddProcessReportRequest report, String token, Throwable exception) {
        LOG.warn("CreateProcessReportFallback method used");
        LOG.warn(exception);
        return true;
    }


    @HystrixCommand(commandKey = "getProcessReportByEmail", fallbackMethod = "getProcessReportByEmailFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public ProcessReportResponse getProcessReportByEmail(String userName, String token) {
        String uri = baseURL + "/" + userName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        Map<String, String> vars = new HashMap<>();
        vars.put("email", userName);

        HttpEntity<ProcessReportResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<ProcessReportResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ProcessReportResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public ProcessReportResponse getProcessReportByEmailFallback(String userName, String token, Throwable exception) {
        LOG.warn("getProcessReportByEmailFallback method used");
        LOG.warn(exception);
        /*
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
        /**/
        return null;
    }


}
