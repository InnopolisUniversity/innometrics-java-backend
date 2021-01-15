package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.BugReportRequest;
import com.innopolis.innometrics.restapi.DTO.BugTrackingListRequest;
import com.innopolis.innometrics.restapi.DTO.BugTrackingRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
//@Transactional
public class BugTrackingService {

    @Autowired
    private RestTemplate restTemplate;

    private static Logger LOG = LogManager.getLogger();

    private String baseURL = "http://INNOMETRICS-COLLECTOR-SERVER/V1/Bug";


    @HystrixCommand( commandKey = "createBug", fallbackMethod = "createBugFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public boolean createBug(BugReportRequest bug, String Token){
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", Token);

        HttpEntity<BugReportRequest> entity = new HttpEntity<>(bug, headers);
        try {
            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);

            HttpStatus status = response.getStatusCode();

            return status == HttpStatus.OK;
        } catch (Exception e) {
            LOG.warn(e);
            return false;
        }

    }

    public boolean createBugFallback(BugTrackingRequest bug, String Token, Throwable exception) {
        LOG.warn("createBugFallback method used");
        LOG.warn(exception);
        return false;
    }


    @HystrixCommand( commandKey = "updateBug", fallbackMethod = "updateBugFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public boolean updateBug(BugTrackingRequest bug, String Token){
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", Token);

        HttpEntity<BugTrackingRequest> entity = new HttpEntity<>(bug, headers);
        try {
            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Object.class);

            HttpStatus status = response.getStatusCode();

            return status == HttpStatus.OK;
        } catch (Exception e) {
            LOG.warn(e);
            return false;
        }
    }

    public boolean updateBugFallback(BugTrackingRequest bug, String Token, Throwable exception) {
        LOG.warn("updateBugFallback method used");
        LOG.warn(exception);
        return false;
    }

    @HystrixCommand( commandKey = "findBugById", fallbackMethod = "findBugByIdFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public BugTrackingRequest findBugById(Integer id, String Token){
        String uri = baseURL ;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", Token);

        HttpEntity<BugTrackingRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("bugId", id);

        ResponseEntity<BugTrackingRequest> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, BugTrackingRequest.class);


        return response.getBody();
    }

    public BugTrackingRequest findBugByIdFallback(Integer id, String Token, Throwable exception) {
        LOG.warn("findBugByIdFallback method used");
        LOG.warn(exception);
        return new BugTrackingRequest();
    }


    @HystrixCommand( commandKey = "findBugsByParameters", fallbackMethod = "findBugsByParametersFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public BugTrackingListRequest findBugsByParameters(Integer status, String creationdate1, String creationdate2, String Token){

        String uri = baseURL + "/all";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", Token);

        HttpEntity<BugTrackingListRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("creationdateFrom", creationdate1)
                .queryParam("creationdateTo", creationdate2)
                .queryParam("status", status);

        ResponseEntity<BugTrackingListRequest> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, BugTrackingListRequest.class);


        return response.getBody();

    }

    public BugTrackingListRequest findBugsByParametersFallback(Integer status,String creationdate1, String creationdate2,
                                                                String Token, Throwable exception) {
        LOG.warn("findBugsByParametersFallback method used");
        LOG.warn(exception);
        return new BugTrackingListRequest();
    }

    @HystrixCommand( commandKey = "deleteBug", fallbackMethod = "deleteBugFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public void deleteBug(Integer id, String token) {

        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<BugTrackingRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("bugId", id);


        restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, Object.class);

    }

    public void deleteBugFallback(Integer id, String Token, Throwable exception) {
        LOG.warn("deleteBugFallback method used");
        LOG.warn(exception);
    }

}
