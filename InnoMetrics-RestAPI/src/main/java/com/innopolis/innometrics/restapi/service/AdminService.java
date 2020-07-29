package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.*;
import com.innopolis.innometrics.restapi.entitiy.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {
    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    private String baseURL = "http://INNOMETRICS-AUTH-SERVER/AdminAPI";


    @HystrixCommand(commandKey = "CreateProject",
            fallbackMethod = "CreateProjectFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ProjectResponse CreateProject(ProjectRequest project, String token) {
        String uri = baseURL + "/Project";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<ProjectRequest> entity = new HttpEntity<>(project, headers);
        try {
            ResponseEntity<ProjectResponse> response = restTemplate.exchange(uri, HttpMethod.POST, entity, ProjectResponse.class);

            HttpStatus status = response.getStatusCode();

            return response.getBody();
        } catch (Exception e) {
            LOG.warn(e);
            return new ProjectResponse();
        }

    }

    public ProjectResponse CreateProjectFallback(ProjectRequest project, String token, Throwable exception) {
        LOG.warn("CreateProjectFallback method used");
        LOG.warn(exception);
        return new ProjectResponse();
    }


    @HystrixCommand(commandKey = "updateProject",
            fallbackMethod = "updateProjectFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ProjectResponse updateProject(ProjectRequest project, String token) {
        String uri = baseURL + "/Project";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<ProjectRequest> entity = new HttpEntity<>(project, headers);
        try {
            ResponseEntity<ProjectResponse> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, ProjectResponse.class);

            HttpStatus status = response.getStatusCode();

            return response.getBody();// status == HttpStatus.OK;
        } catch (Exception e) {
            LOG.warn(e);
            return new ProjectResponse();
        }

    }

    public ProjectResponse updateProjectFallback(ProjectRequest project, String token, Throwable exception) {
        LOG.warn("updateProjectFallback method used");
        LOG.warn(exception);
        return new ProjectResponse();
    }


    @HystrixCommand(commandKey = "getActiveProjects",
            fallbackMethod = "getActiveProjectsFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ProjectListResponse getActiveProjects() {
        String uri = baseURL + "/Project";

        HttpHeaders headers = new HttpHeaders();
        //headers.set("Token", token);


        //HttpEntity<ProjectListRequest> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<ProjectListResponse> response = restTemplate.exchange(uri, HttpMethod.GET, null, ProjectListResponse.class);

            HttpStatus status = response.getStatusCode();

            return response.getBody();
        } catch (Exception e) {
            LOG.warn(e);
            return null;
        }

    }

    public ProjectListResponse getActiveProjectsFallback(Throwable exception) {
        LOG.warn("getActiveProjectsFallback method used");
        LOG.warn(exception);
        return null;
    }


    @HystrixCommand(commandKey = "getActiveUsers",
            fallbackMethod = "getActiveUsersFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public UserListResponse getActiveUsers(String request) {
        String uri = baseURL + "/Users";

        HttpHeaders headers = new HttpHeaders();
        //headers.set("Token", token);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("projectId", request);


        //HttpEntity<UserListRequest> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<UserListResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, UserListResponse.class);

            HttpStatus status = response.getStatusCode();

            return response.getBody();
        } catch (Exception e) {
            LOG.warn(e);
            return null;
        }

    }

    public UserListResponse getActiveUsersFallback(String request, Throwable exception) {
        LOG.warn("getActiveUsersFallback method used");
        LOG.warn(exception);
        return null;
    }


    @HystrixCommand(commandKey = "getProjectsByUsername",
            fallbackMethod = "getProjectsByUsernameFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ProjectListResponse getProjectsByUsername(String userName) {
        //ProjectListResponse response = new ProjectListResponse();
        String uri = baseURL + "/Users/projects/" + userName;

        ResponseEntity<ProjectListResponse> response = null;

        HttpEntity<String> entity = new HttpEntity<>(null);
        response = restTemplate.exchange(uri, HttpMethod.GET, entity, ProjectListResponse.class);

        return response.getBody();
    }

    public ProjectListResponse getProjectsByUsernameFallback(String userName, Throwable exception) {
        LOG.warn("getProjectsByUsernameFallback method used");
        LOG.warn(exception);
        return null;
    }
}
