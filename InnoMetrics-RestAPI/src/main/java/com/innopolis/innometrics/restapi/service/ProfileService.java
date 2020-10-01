package com.innopolis.innometrics.restapi.service;


import com.innopolis.innometrics.restapi.DTO.AddProcessReportRequest;
import com.innopolis.innometrics.restapi.DTO.ProfileRequest;
import com.innopolis.innometrics.restapi.DTO.UserListResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ProfileService {

    private static Logger LOG = LogManager.getLogger();


    @Autowired
    private RestTemplate restTemplate;

    private String baseURL = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/User/Profile";


    @HystrixCommand(commandKey = "updateProfileOfUser", fallbackMethod = "updateProfileOfUserFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public ProfileRequest updateProfileOfUser(ProfileRequest profileRequest, String token){

        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<ProfileRequest> entity = new HttpEntity<>(profileRequest, headers);

        ResponseEntity<ProfileRequest> response = restTemplate.exchange(uri, HttpMethod.POST, entity, ProfileRequest.class);

        return response.getBody();
    }


    public ProfileRequest updateProfileOfUserFallback(ProfileRequest profileRequest, String token, Throwable exception) {
        LOG.warn("updateProfileOfUserFallback method used");
        LOG.warn(exception);
        return null;
    }

    @HystrixCommand(commandKey = "deleteProfile", fallbackMethod = "deleteProfileFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public boolean deleteProfile(Integer id,String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<ProfileRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id", id);


        restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, Object.class);
        return true;

    }

    public boolean deleteProfileFallback(Integer id, String token, Throwable exception) {
        LOG.warn("deleteProfileFallback method used");
        LOG.warn(exception);
        return false;
    }

    @HystrixCommand(commandKey = "findByMacaddress", fallbackMethod = "findByMacaddressFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "600000")
    })
    public ProfileRequest findByMacaddress(String macaddress, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("macaddress", macaddress);

        ResponseEntity<ProfileRequest> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, ProfileRequest.class);

        HttpStatus status = responseEntity.getStatusCode();
        return responseEntity.getBody();

    }

    public ProfileRequest findByMacaddressFallback(String macaddress, String token, Throwable exception) {
        LOG.warn("findByMacaddressFallback method used");
        LOG.warn(exception);
        return null;
    }
}
