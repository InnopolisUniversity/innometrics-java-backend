package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.ActivitiesReportByUserResponse;
import com.innopolis.innometrics.restapi.DTO.CumulativeReportResponse;
import com.innopolis.innometrics.restapi.DTO.TimeReportResponse;
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
public class ReportService {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    private String baseURL = "http://INNOMETRICS-COLLECTOR-SERVER/V1/Reports";

    @HystrixCommand(commandKey = "getReportActivities", fallbackMethod = "getReportActivitiesFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public ActivitiesReportByUserResponse getReportActivities(String projectID,
                                                              String email,
                                                              Date min_Date,
                                                              Date max_Date) {
        String uri = baseURL + "/activitiesReport";

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("projectID", projectID)
                .queryParam("email", email)
                .queryParam("min_Date", min_Date)
                .queryParam("max_Date", max_Date);
        ResponseEntity<ActivitiesReportByUserResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, ActivitiesReportByUserResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();

    }

    public ActivitiesReportByUserResponse getReportActivitiesFallback(String projectID,
                                                                      String email,
                                                                      Date min_Date,
                                                                      Date max_Date, Throwable exception) {
        LOG.warn("getReportActivitiesFallback method used");
        LOG.warn(exception);
        return new ActivitiesReportByUserResponse();
    }

    @HystrixCommand(commandKey = "getTimeReport", fallbackMethod = "getTimeReportFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public TimeReportResponse getTimeReport(String projectID,
                                            String email,
                                            Date min_Date,
                                            Date max_Date) {
        String uri = baseURL + "/timeReport";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("projectId", projectID)
                .queryParam("email", email)
                .queryParam("min_Date", min_Date)
                .queryParam("max_Date", max_Date);
        ResponseEntity<TimeReportResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, TimeReportResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();

    }

    public TimeReportResponse getTimeReportFallback(String projectID,
                                                    String email,
                                                    Date min_Date,
                                                    Date max_Date, Throwable exception) {
        LOG.warn("getTimeReportFallback method used");
        LOG.warn(exception);
        return new TimeReportResponse();
    }


    @HystrixCommand(commandKey = "getCumulativeReport", fallbackMethod = "getCumulativeReportFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CumulativeReportResponse getCumulativeReport(String email,
                                                        Date min_Date,
                                                        Date max_Date) {
        String uri = baseURL + "/cumulativeReport";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Format formatter =  new SimpleDateFormat("dd/MM/yyyy");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("email", email)
                .queryParam("min_Date", min_Date != null ? formatter.format(min_Date) : null)
                .queryParam("max_Date", max_Date != null ? formatter.format(max_Date) : null);
        ResponseEntity<CumulativeReportResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CumulativeReportResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();

    }

    public CumulativeReportResponse getCumulativeReportFallback(String email,
                                                                 Date min_Date,
                                                                 Date max_Date, Throwable exception) {
        LOG.warn("getCumulativeReportFallback method used");
        LOG.warn(exception);
        return new CumulativeReportResponse();
    }
}
