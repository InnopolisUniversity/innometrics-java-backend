package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

@Service
@Transactional
public class CategoryService {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    private String baseURL = "http://INNOMETRICS-COLLECTOR-SERVER/V1/Classification";


    @HystrixCommand(commandKey = "addCategory", fallbackMethod = "addCategoryFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CategoryResponse addCategory(CategoryRequest categoryRequest, String token) {
        String uri = baseURL + "/Category";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<CategoryRequest> entity = new HttpEntity<>(categoryRequest, headers);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        ResponseEntity<CategoryResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, CategoryResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public CategoryResponse addCategoryFallback(CategoryRequest categoryRequest, String token, Throwable exception) {
        LOG.warn("addCategoryFallback method used");
        LOG.warn(exception);
        return new CategoryResponse();
    }


    @HystrixCommand(commandKey = "UpdateCategory", fallbackMethod = "UpdateCategoryFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CategoryResponse UpdateCategory(CategoryRequest categoryRequest, String token) {
        String uri = baseURL + "/Category";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<CategoryRequest> entity = new HttpEntity<>(categoryRequest, headers);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        ResponseEntity<CategoryResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, CategoryResponse.class);

        HttpStatus status = response.getStatusCode();


        return response.getBody();
    }

    public CategoryResponse UpdateCategory(CategoryRequest categoryRequest, String token, Throwable exception) {
        LOG.warn("UpdateCategory method used");
        LOG.warn(exception);
        return new CategoryResponse();
    }


    @HystrixCommand(commandKey = "getCategoryById", fallbackMethod = "getCategoryByIdFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CategoryResponse getCategoryById(Integer categoryid, String token) {
        String uri = baseURL + "/Category";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<CategoryRequest> entity = new HttpEntity<>(headers);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("CategoryId", categoryid);

        ResponseEntity<CategoryResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CategoryResponse.class);

        HttpStatus status = response.getStatusCode();


        return response.getBody();
    }

    public CategoryResponse getCategoryByIdFallback(Integer categoryid, String token, Throwable exception) {
        LOG.warn("getCategoryByIdFallback method used");
        LOG.warn(exception);
        return new CategoryResponse();
    }


    @HystrixCommand(commandKey = "addAppCategory", fallbackMethod = "addAppCategoryFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public AppCategoryResponse addAppCategory(AppCategoryRequest appcategoryRequest, String token) {
        String uri = baseURL + "/App";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<AppCategoryRequest> entity = new HttpEntity<>(appcategoryRequest, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        ResponseEntity<AppCategoryResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, AppCategoryResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public AppCategoryResponse addAppCategoryFallback(AppCategoryRequest appcategoryRequest, String token, Throwable exception) {
        LOG.warn("addAppCategoryFallback method used");
        LOG.warn(exception);
        return new AppCategoryResponse();
    }


    @HystrixCommand(commandKey = "UpdateAppCategory", fallbackMethod = "UpdateAppCategoryFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public AppCategoryResponse UpdateAppCategory(AppCategoryRequest appcategoryRequest, String token) {
        String uri = baseURL + "/App";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<AppCategoryRequest> entity = new HttpEntity<>(appcategoryRequest, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        ResponseEntity<AppCategoryResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, AppCategoryResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public AppCategoryResponse UpdateAppCategoryFallback(AppCategoryRequest appcategoryRequest, String token, Throwable exception) {
        LOG.warn("UpdateAppCategoryFallback method used");
        LOG.warn(exception);
        return new AppCategoryResponse();
    }

    @HystrixCommand(commandKey = "getAppCategoryById", fallbackMethod = "getAppCategoryByIdFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public AppCategoryResponse getAppCategoryById(Integer AppId, String token) {
        String uri = baseURL + "/App";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("AppId", AppId);

        ResponseEntity<AppCategoryResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, AppCategoryResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public AppCategoryResponse getAppCategoryByIdFallback(Integer AppId, String token, Throwable exception) {
        LOG.warn("getAppCategoryByIdFallback method used");
        LOG.warn(exception);
        return new AppCategoryResponse();
    }




    @HystrixCommand(commandKey = "getAllCategories", fallbackMethod = "getAllCategoriesFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CategoryListResponse getAllCategories(String token) {
        String uri = baseURL + "/Category/GetAll";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        ResponseEntity<CategoryListResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CategoryListResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }

    public CategoryListResponse getAllCategoriesFallback(String token, Throwable exception) {
        LOG.warn("getAppCategoryByIdFallback method used");
        LOG.warn(exception);
        return new CategoryListResponse();
    }





    @HystrixCommand(commandKey = "getTimeReport", fallbackMethod = "getTimeReportFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public CategoriesTimeReportResponse getTimeReport(String projectID,
                                            String email,
                                            Date min_Date,
                                            Date max_Date) {
        String uri = baseURL + "/timeReport";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("projectID", projectID)
                .queryParam("email", email)
                .queryParam("min_Date", min_Date)
                .queryParam("max_Date", max_Date);
        ResponseEntity<CategoriesTimeReportResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, CategoriesTimeReportResponse.class);

        HttpStatus status = response.getStatusCode();

        return response.getBody();

    }

    public CategoriesTimeReportResponse getTimeReportFallback(String projectID,
                                                    String email,
                                                    Date min_Date,
                                                    Date max_Date, Throwable exception) {
        LOG.warn("getTimeReportFallback method used");
        LOG.warn(exception);
        return new CategoriesTimeReportResponse();
    }
}
