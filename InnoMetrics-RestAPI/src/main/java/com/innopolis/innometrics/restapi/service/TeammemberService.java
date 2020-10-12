package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.TeammembersListRequest;
import com.innopolis.innometrics.restapi.DTO.TeammembersRequest;
import com.innopolis.innometrics.restapi.DTO.WorkingTreeListRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TeammemberService {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;
    private String baseURL = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/Teammember";


    @HystrixCommand(commandKey = "updateTeammember", fallbackMethod = "updateTeammemberFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public TeammembersRequest updateTeammember(TeammembersRequest teammembersRequest, String token){

        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<TeammembersRequest> entity = new HttpEntity<>(teammembersRequest, headers);

        ResponseEntity<TeammembersRequest> response = restTemplate.exchange(uri, HttpMethod.POST, entity, TeammembersRequest.class);

        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.NO_CONTENT) return null;

        return response.getBody();
    }


    public TeammembersRequest updateTeammemberFallback(TeammembersRequest teammembersRequest, String token, Throwable exception) {
        LOG.warn("updateTeammemberFallback method used");
        LOG.warn(exception);
        return new TeammembersRequest();
    }


    public boolean deleteTeammember(Integer id, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<TeammembersRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id", id);

        try {
            restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, Object.class);
            return true;
        }
        catch (Exception e){
            LOG.error(e);
            return false;
        }
    }

    @HystrixCommand(commandKey = "getActiveTeammembers", fallbackMethod = "getActiveTeammembersFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public TeammembersListRequest getActiveTeammembers(String token) {
        String uri =baseURL + "/all";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        ResponseEntity<TeammembersListRequest> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, TeammembersListRequest.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public TeammembersListRequest getActiveTeammembersFallback(String token, Throwable exception) {
        LOG.warn("getActiveTeammembersFallback method used");
        LOG.warn(exception);
        return new TeammembersListRequest();
    }

    @HystrixCommand(commandKey = "getTeammembersBy", fallbackMethod = "getTeammembersByFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public TeammembersListRequest getTeammembersBy(Integer memberId, Integer teamId, String email, String Token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", Token);

        HttpEntity<TeammembersListRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("teamId", teamId).queryParam("memberId", memberId).queryParam("email", email);

        ResponseEntity<TeammembersListRequest> responseEntity = restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,TeammembersListRequest.class);

        return responseEntity.getBody();

    }

    public TeammembersListRequest getTeammembersByFallback(Integer memberId, Integer teamId, String email, String Token, Throwable exception) {
        LOG.warn("getTeammembersByFallback method used");
        LOG.warn(exception);
        return new TeammembersListRequest();
    }

    @HystrixCommand(commandKey = "getWorkingTree", fallbackMethod = "getWorkingTreeFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public WorkingTreeListRequest getWorkingTree(String email, String Token) {
        String uri = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/WorkingTree";;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", Token);

        HttpEntity<WorkingTreeListRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("email", email);

        ResponseEntity<WorkingTreeListRequest> responseEntity = restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,WorkingTreeListRequest.class);

        return responseEntity.getBody();

    }

    public WorkingTreeListRequest getWorkingTreeFallback(String email, String Token, Throwable exception) {
        LOG.warn("getWorkingTreeFallback method used");
        LOG.warn(exception);
        return new WorkingTreeListRequest();
    }
}
