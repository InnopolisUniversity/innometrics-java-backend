package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.CompanyListRequest;
import com.innopolis.innometrics.restapi.DTO.CompanyRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class CompanyService {

    private static Logger LOG = LogManager.getLogger();


    @Autowired
    private RestTemplate restTemplate;

    private String baseURL = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/Company";

    @HystrixCommand(commandKey = "updateCompany", fallbackMethod = "updateCompanyFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CompanyRequest updateCompany(CompanyRequest companyRequest, String token) {
        String uri = baseURL + "/" + companyRequest.getCompanyid();
        return uploadCompany(companyRequest, token, uri, HttpMethod.PUT);
    }

    public CompanyRequest createCompany(CompanyRequest companyRequest, String token) {
        String uri = baseURL;
        return uploadCompany(companyRequest, token, uri, HttpMethod.POST);
    }

    private CompanyRequest uploadCompany(CompanyRequest companyRequest, String token, String uri, HttpMethod method) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<CompanyRequest> entity = new HttpEntity<>(companyRequest, headers);

        ResponseEntity<CompanyRequest> response = restTemplate.exchange(uri, method, entity, CompanyRequest.class);

        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.NO_CONTENT) return null;

        return response.getBody();
    }


    public CompanyRequest updateCompanyFallback(CompanyRequest companyRequest, String token, Throwable exception) {
        LOG.warn("updateCompanyFallback method used");
        LOG.warn(exception);
        return new CompanyRequest();
    }

    public boolean deleteCompany(Integer id, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<CompanyRequest> entity = new HttpEntity<>(headers);

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


    @HystrixCommand(commandKey = "findByCompanyId", fallbackMethod = "findByCompanyIdFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CompanyRequest findByCompanyId(Integer id, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<CompanyRequest> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("id", id);

        ResponseEntity<CompanyRequest> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CompanyRequest.class);

        return responseEntity.getBody();

    }

    public CompanyRequest findByCompanyIdFallback(Integer id, String token, Throwable exception) {
        LOG.warn("findByCompanyIdFallback method used");
        LOG.warn(exception);
        return new CompanyRequest();
    }

    @HystrixCommand(commandKey = "getActiveCompanies", fallbackMethod = "getActiveCompaniesFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CompanyListRequest getAllActiveCompanies(String token) {
        String uri = baseURL + "/active";
        return getCompanies(token, uri);
    }

    @HystrixCommand(commandKey = "getAllCompanies", fallbackMethod = "getAllCompaniesFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CompanyListRequest getAllCompanies(String token) {
        String uri = baseURL + "/all";
        return getCompanies(token, uri);
    }

    private CompanyListRequest getCompanies(String token, String uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        ResponseEntity<CompanyListRequest> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CompanyListRequest.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public CompanyListRequest getActiveCompaniesFallback(String token, Throwable exception) {
        LOG.warn("getActiveCompaniesFallback method used");
        LOG.warn(exception);
        return new CompanyListRequest();
    }

}
