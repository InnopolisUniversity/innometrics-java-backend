package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.*;
import com.innopolis.innometrics.restapi.entity.Activity;
import com.innopolis.innometrics.restapi.entity.Measurement;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ActivityRepository activityRepository;


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



    @HystrixCommand(commandKey = "getActivitiesByEmail", fallbackMethod = "getActivitiesByEmailFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public ActivitiesReportResponse getActivitiesByEmail(Date reportDate, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        Format formatter =  new SimpleDateFormat("dd/MM/yyyy");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURL)
                .queryParam("reportDate", reportDate != null ? formatter.format(reportDate) : null);


        HttpEntity<ActivitiesReportResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<ActivitiesReportResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, ActivitiesReportResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public ActivitiesReportResponse getActivitiesByEmailFallback(Date reportDate, String token, Throwable exception) {
        LOG.warn("getActivitiesByEmailFallback method used");
        LOG.warn(exception);
        return null;
    }


    @HystrixCommand( commandKey = "deleteActivitiesWithIds", fallbackMethod = "deleteActivitiesWithIdsFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public boolean deleteActivitiesWithIds(DeleteRequest request, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<DeleteRequest> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);

            HttpStatus status = response.getStatusCode();

            return status == HttpStatus.OK;
        } catch (Exception e) {
            LOG.warn(e);
            return false;
        }

    }

    public boolean deleteActivitiesWithIdsFallback(DeleteRequest request, String token, Throwable exception) {
        LOG.warn("deleteActivitiesWithIdsFallback method used");
        LOG.warn(exception);
        return false;
    }



}
