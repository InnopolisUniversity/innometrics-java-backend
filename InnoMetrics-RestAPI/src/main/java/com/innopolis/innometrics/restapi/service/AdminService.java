package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AdminService {
    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    private String baseURL = "http://INNOMETRICS-AUTH-SERVER/AdminAPI";


    @HystrixCommand(commandKey = "updateProject",
            fallbackMethod = "updateProjectFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ProjectRequest updateProject(ProjectRequest project, String token) {
        String uri = baseURL + "/Project";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<ProjectRequest> entity = new HttpEntity<>(project, headers);
        try {
            ResponseEntity<ProjectRequest> response = restTemplate.exchange(uri, HttpMethod.POST, entity, ProjectRequest.class);

            HttpStatus status = response.getStatusCode();

            return response.getBody();
        } catch (Exception e) {
            LOG.warn(e);
            return new ProjectRequest();
        }

    }

    public ProjectRequest updateProjectFallback(ProjectRequest project, String token, Throwable exception) {
        LOG.warn("CreateProjectFallback method used");
        LOG.warn(exception);
        return new ProjectRequest();
    }

    @HystrixCommand(commandKey = "deleteProject",
            fallbackMethod = "deleteProjectFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ProjectRequest deleteProject(Integer projectId, String token) {
        String uri = baseURL + "/Project";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<ProjectRequest> entity = new HttpEntity<>(null, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id", projectId);

        try {
            ResponseEntity<ProjectRequest> response = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, ProjectRequest.class);

            HttpStatus status = response.getStatusCode();

            return response.getBody();
        } catch (Exception e) {
            LOG.warn(e);
            return null;
        }

    }

    public ProjectRequest deleteProjectFallback(Integer projectId, String token, Throwable exception) {
        LOG.warn("deleteProjectFallback method used");
        LOG.warn(exception);
        return null;
    }

    @HystrixCommand(commandKey = "getProject",
            fallbackMethod = "getProjectFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ProjectRequest getById(int id) {
        String uri = baseURL + "/Project/" + id;

        //HttpHeaders headers = new HttpHeaders();
        //headers.set("Token", token);




        //HttpEntity<UserListRequest> entity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<ProjectRequest> response = restTemplate.exchange(uri, HttpMethod.GET, null, ProjectRequest.class);
            HttpStatus status = response.getStatusCode();

            //if (status==HttpStatus.NOT_FOUND)
               // return
            return response.getBody();
        } catch (Exception e) {
            LOG.warn(e);
            return null;
        }

    }

    public ProjectRequest getProjectFallback(int id, Throwable exception) {
        LOG.warn("getProjectFallback method used");
        LOG.warn(exception);
        return new ProjectRequest();
    }



    @HystrixCommand(commandKey = "getActiveProjects",
            fallbackMethod = "getActiveProjectsFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ProjectListRequest getActiveProjects() {
        String uri = baseURL + "/Project/active";

        HttpHeaders headers = new HttpHeaders();
        //headers.set("Token", token);


        //HttpEntity<ProjectListRequest> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<ProjectListRequest> response = restTemplate.exchange(uri, HttpMethod.GET, null, ProjectListRequest.class);

            HttpStatus status = response.getStatusCode();

            return response.getBody();
        } catch (Exception e) {
            LOG.warn(e);
            return null;
        }

    }

    @HystrixCommand(commandKey = "getAllProjects",
            fallbackMethod = "getAllProjectsFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public ProjectListRequest getAllProjects() {
        String uri = baseURL + "/Project/all";

        HttpHeaders headers = new HttpHeaders();
        //headers.set("Token", token);


        //HttpEntity<ProjectListRequest> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<ProjectListRequest> response = restTemplate.exchange(uri, HttpMethod.GET, null, ProjectListRequest.class);

            HttpStatus status = response.getStatusCode();

            return response.getBody();
        } catch (Exception e) {
            LOG.warn(e);
            return null;
        }

    }

    public ProjectListRequest getActiveProjectsFallback(Throwable exception) {
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
    public ProjectListRequest getProjectsByUsername(String userName) {
        //ProjectListResponse response = new ProjectListResponse();
        String uri = baseURL + "/Users/projects/" + userName;

        ResponseEntity<ProjectListRequest> response = null;

        HttpEntity<String> entity = new HttpEntity<>(null);
        response = restTemplate.exchange(uri, HttpMethod.GET, entity, ProjectListRequest.class);

        return response.getBody();
    }

    public ProjectListRequest getProjectsByUsernameFallback(String userName, Throwable exception) {
        LOG.warn("getProjectsByUsernameFallback method used");
        LOG.warn(exception);
        return null;
    }
}
