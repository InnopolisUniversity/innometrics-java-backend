package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.TeamListRequest;
import com.innopolis.innometrics.restapi.DTO.TeamRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TeamService {
    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;
    private String baseURL = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/Team";


    @HystrixCommand(commandKey = "updateTeam", fallbackMethod = "updateTeamFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public TeamRequest updateTeam(TeamRequest teamRequest, String token) {
        String uri = baseURL + "/" + teamRequest.getTeamid();
        return uploadTeam(teamRequest, token, uri, HttpMethod.PUT);
    }

    public TeamRequest createTeam(TeamRequest teamRequest, String token) {
        String uri = baseURL;
        return uploadTeam(teamRequest, token, uri, HttpMethod.POST);
    }

    private TeamRequest uploadTeam(TeamRequest teamRequest, String token, String uri, HttpMethod method) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<TeamRequest> entity = new HttpEntity<>(teamRequest, headers);

        ResponseEntity<TeamRequest> response = restTemplate.exchange(uri, method, entity, TeamRequest.class);

        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.NO_CONTENT) return null;

        return response.getBody();
    }


    public TeamRequest updateTeamFallback(TeamRequest teamRequest, String token, Throwable exception) {
        LOG.warn("updateTeamFallback method used");
        LOG.warn(exception);
        return new TeamRequest();
    }

    public boolean deleteTeam(Integer id, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<TeamRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id", id);

        try {
            restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, Object.class);
            return true;
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    @HystrixCommand(commandKey = "getActiveTeams", fallbackMethod = "getActiveTeamsFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public TeamListRequest getActiveTeams(String token) {
        String uri = baseURL + "/all";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        ResponseEntity<TeamListRequest> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, TeamListRequest.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public TeamListRequest getActiveTeamsFallback(String token, Throwable exception) {
        LOG.warn("getActiveTeamsFallback method used");
        LOG.warn(exception);
        return new TeamListRequest();
    }

    @HystrixCommand(commandKey = "getTeamsBy", fallbackMethod = "getTeamsByFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public TeamListRequest getTeamsBy(Integer teamId, Integer companyId, Integer projectId, String Token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", Token);

        HttpEntity<TeamListRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("teamId", teamId).queryParam("companyId", companyId).queryParam("projectId", projectId);

        ResponseEntity<TeamListRequest> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, TeamListRequest.class);

        return responseEntity.getBody();

    }

    public TeamListRequest getTeamsByFallback(Integer teamId, Integer companyId, Integer projectId, String token, Throwable exception) {
        LOG.warn("getTeamsByFallback method used");
        LOG.warn(exception);
        return new TeamListRequest();
    }

}

