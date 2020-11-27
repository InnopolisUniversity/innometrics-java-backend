package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.PermissionResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PermissionService {

    @Autowired
    private RestTemplate restTemplate;
    private String baseURL = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/Role/Permissions";
    private static Logger LOG = LogManager.getLogger();

    @HystrixCommand(commandKey = "getPermissiosOfUser", fallbackMethod = "getPermissiosOfUserFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public PermissionResponse getPermissiosOfUser(String userName){

        PermissionResponse permissionResponse;
        String uri = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/User/Permissions/"  + userName;


        HttpEntity<PermissionResponse> entity = new HttpEntity<>(null);
        ResponseEntity<PermissionResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, PermissionResponse.class);

        HttpStatus status = response.getStatusCode();
        permissionResponse = response.getBody();

        if (status == HttpStatus.NO_CONTENT) return null;

        return permissionResponse;
    }

    public PermissionResponse getPermissiosOfUserFallback(String userName, Throwable exception) {
        LOG.warn("getPermissiosOfUserFallback method used");
        LOG.warn(exception);
        return null;
    }

    @HystrixCommand(commandKey = "createPermissios", fallbackMethod = "createPermissiosFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public PermissionResponse createPermissios(PermissionResponse permissionResponse){

        PermissionResponse permissionResponseOut;
        String uri = baseURL;


        HttpEntity<PermissionResponse> entity = new HttpEntity<>(permissionResponse);
        ResponseEntity<PermissionResponse> response = restTemplate.exchange(uri, HttpMethod.POST, entity, PermissionResponse.class);

        HttpStatus status = response.getStatusCode();
        permissionResponseOut = response.getBody();

        if (status == HttpStatus.NO_CONTENT) return null;

        return permissionResponseOut;
    }

    public PermissionResponse createPermissiosFallback(PermissionResponse permissionRespons, Throwable exception) {
        LOG.warn("getPermissiosOfUserFallback method used");
        LOG.warn(exception);
        return null;
    }


}
