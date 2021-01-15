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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ProcessService {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    private String baseURL = "http://INNOMETRICS-COLLECTOR-SERVER/V1/process/";


    @HystrixCommand(commandKey = "CreateProcessReport", fallbackMethod = "CreateProcessReportFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public boolean CreateProcessReport(AddProcessReportRequest report, String token) {
        String uri = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<AddProcessReportRequest> entity = new HttpEntity<>(report, headers);
        ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);

        HttpStatus status = response.getStatusCode();

        return status == HttpStatus.OK;

    }

    public boolean CreateProcessReportFallback(AddProcessReportRequest report, String token, Throwable exception) {
        LOG.warn("CreateProcessReportFallback method used");
        LOG.warn(exception);
        return true;
    }


    @HystrixCommand(commandKey = "getProcessReportByEmail", fallbackMethod = "getProcessReportByEmailFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public ProcessDayReportResponse getProcessReportByEmail(Date reportDate, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        Format formatter =  new SimpleDateFormat("dd/MM/yyyy");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURL)
                .queryParam("reportDate", reportDate != null ? formatter.format(reportDate) : null);


        HttpEntity<ProcessDayReportResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<ProcessDayReportResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, ProcessDayReportResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public ProcessDayReportResponse getProcessReportByEmailFallback(Date reportDate, String token, Throwable exception) {
        LOG.warn("getProcessReportByEmailFallback method used");
        LOG.warn(exception);
        return null;
    }


    @HystrixCommand( commandKey = "deleteProcessesWithIds", fallbackMethod = "deleteProcessesWithIdsFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public boolean deleteProcessesWithIds(DeleteRequest request, String token) {
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

    public boolean deleteProcessesWithIdsFallback(DeleteRequest request, String token, Throwable exception) {
        LOG.warn("deleteProcessesWithIdsFallback method used");
        LOG.warn(exception);
        return false;
    }


}
