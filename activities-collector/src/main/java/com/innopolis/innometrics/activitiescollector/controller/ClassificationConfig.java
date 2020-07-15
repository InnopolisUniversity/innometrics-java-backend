package com.innopolis.innometrics.activitiescollector.controller;


import com.innopolis.innometrics.activitiescollector.DTO.*;
import com.innopolis.innometrics.activitiescollector.config.JwtToken;
import com.innopolis.innometrics.activitiescollector.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;

@RestController
@RequestMapping(value = "/V1/Classification", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClassificationConfig {
    @Autowired
    CategoryService categoryService;

    @Autowired
    private JwtToken jwtTokenUtil;

    @PostMapping("/Category")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest,
                                                        UriComponentsBuilder ucBuilder,
                                                        @RequestHeader(required = false) String Token) {
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtTokenUtil.getUsernameFromToken(Token);

        CategoryResponse response = categoryService.CreateCategory(categoryRequest, UserName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/Category/GetAll")
    public ResponseEntity<CategoryListResponse> getAllCategories(@RequestHeader(required = false) String Token) {

        CategoryListResponse response = categoryService.getAllCategories();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/Category")
    public ResponseEntity<CategoryResponse> getCategoryById(@RequestParam Integer CategoryId, @RequestHeader(required = false) String Token) {

        CategoryResponse response = categoryService.getCategoryById(CategoryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/Category")
    public ResponseEntity<CategoryResponse> UpdateCategory(@RequestBody CategoryRequest categoryRequest,
                                                           UriComponentsBuilder ucBuilder,
                                                           @RequestHeader(required = false) String Token) {
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtTokenUtil.getUsernameFromToken(Token);

        CategoryResponse response = categoryService.UpdateCategory(categoryRequest, UserName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping("/App")
    public ResponseEntity<AppCategoryResponse> addAppCategory(@RequestBody AppCategoryRequest appCategoryRequest,
                                                           UriComponentsBuilder ucBuilder,
                                                           @RequestHeader(required = false) String Token) {
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtTokenUtil.getUsernameFromToken(Token);

        AppCategoryResponse response = categoryService.CreateAppCategory(appCategoryRequest, UserName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/App")
    public ResponseEntity<AppCategoryResponse> getAppCategoryById(@RequestParam Integer AppId, @RequestHeader(required = false) String Token) {

        AppCategoryResponse response = categoryService.getAppCategoryById(AppId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/App")
    public ResponseEntity<AppCategoryResponse> UpdateAppCategory(@RequestBody AppCategoryRequest appCategoryRequest,
                                                           UriComponentsBuilder ucBuilder,
                                                           @RequestHeader(required = false) String Token) {
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtTokenUtil.getUsernameFromToken(Token);

        AppCategoryResponse response = categoryService.UpdateAppCategory(appCategoryRequest, UserName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/timeReport")
    public ResponseEntity<CategoriesTimeReportResponse> getTimeReport(@RequestParam(required = false) String projectID,
                                                            @RequestParam(required = false) String email,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date min_Date,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date max_Date) {
        projectID = projectID == "" ? null : projectID;
        email = email == "" ? null : email;
        TimeReportRequest request = new TimeReportRequest(projectID, email, min_Date, max_Date);
        CategoriesTimeReportResponse myReport = categoryService.getTimeReport(request);
        return ResponseEntity.ok(myReport);
    }
}
