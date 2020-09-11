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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        //change later to required = true and delete this line
//        String email = "";
//        if (token != null)
//            email = jwtToken.getUsernameFromToken(token);
//
//
//        ProfileRequest response;
//        if(!profileService.existsByEmail(email, profileRequest.getMacAddress())){
//            response = profileService.create(profileRequest);
//        } else {
//            response = profileService.update(profileRequest);
//        }
//
//        return ResponseEntity.ok(response);

        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);

        HttpEntity<ProfileRequest> entity = new HttpEntity<>(profileRequest, headers);

        ResponseEntity<ProfileRequest> response = restTemplate.exchange(uri, HttpMethod.POST, entity, ProfileRequest.class);

        return response.getBody();
    }


    public ProfileRequest updateProfileOfUserFallback(ProfileRequest profileRequest, String token, Throwable exception) {
        LOG.warn("updateProfileOfUserFallback method used");
        LOG.warn(exception);
        return new ProfileRequest();
    }

    public boolean deleteProfile(Integer id,String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id", id);

        try {
            restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, null, Object.class);
            return true;
        }
        catch (Exception e){
            LOG.error(e);
            return false;
        }


    }

    public ProfileRequest findByMacaddress(String macaddress, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("macaddress", macaddress);

        ResponseEntity<ProfileRequest> responseEntity = restTemplate.exchange(builder.toUriString(),HttpMethod.GET,null,ProfileRequest.class);

        return responseEntity.getBody();

    }
}
