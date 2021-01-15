package com.innopolis.innometrics.restapi.controller;

import com.innopolis.innometrics.restapi.DTO.*;
import com.innopolis.innometrics.restapi.config.JwtToken;
import com.innopolis.innometrics.restapi.exceptions.ValidationException;
import com.innopolis.innometrics.restapi.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/V1", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataCollectorsAPI {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private JwtToken jwtTokenUtil;

    @Autowired
    ActivityService activityService;

    @Autowired
    ProcessService processService;

    @Autowired
    ReportService reportService;

    @Autowired
    CategoryService categoryService;


    @GetMapping("/activity")
    public ResponseEntity<ActivitiesReportResponse> getActivities(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date reportDate,
                                                                  @RequestHeader String Token) {
        ActivitiesReportResponse myReport = activityService.getActivitiesByEmail(reportDate, Token);
        return ResponseEntity.ok(myReport);
    }

    @PostMapping("/activity")
    public ResponseEntity<?> addReport(@RequestBody Report report,
                                       UriComponentsBuilder ucBuilder,
                                       @RequestHeader String Token) {

        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        LOG.info("A request received from " + UserName + ", with " + report.getActivities().size() + " activities");
        if(report.getActivities().size() > 1000 || report.getActivities().size() == 0){
            return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
        }
        Boolean result = activityService.CreateActivity(report, Token);
        if (result) return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping("/activity")
    public ResponseEntity<?> deleteActivitiesWithIds(@RequestBody DeleteRequest request,
                                                     @RequestHeader String Token) {
        if (activityService.deleteActivitiesWithIds(request, Token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }



    @GetMapping("/process")
    public ResponseEntity<ProcessDayReportResponse> getProcesses(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date reportDate,
                                                                 @RequestHeader String Token) {
        ProcessDayReportResponse myReport = processService.getProcessReportByEmail(reportDate, Token);
        return ResponseEntity.ok(myReport);
    }

    //add process report
    @PostMapping("/process")
    public ResponseEntity<?> addProcessReport(@RequestBody AddProcessReportRequest report,
                                              UriComponentsBuilder ucBuilder,
                                              @RequestHeader String Token) {

        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        Date CreationDate = new Date();

        LOG.info("A request received from " + UserName + ", with " + report.getProcessesReport().size() + " process");
        if(report.getProcessesReport().size() > 1000 || report.getProcessesReport().size() == 0){
            return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
        }

        Boolean result = processService.CreateProcessReport(report, Token);
        if (result) return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping("/process")
    public ResponseEntity<?> deleteProcessesWithIds(@RequestBody DeleteRequest request,
                                                     @RequestHeader String Token) {
        if (processService.deleteProcessesWithIds(request, Token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


    @GetMapping("/Reports/activitiesReport")
    public ResponseEntity<ActivitiesReportByUserResponse> getReportActivities(@RequestParam(required = false) String projectID,
                                                                              @RequestParam(required = false) String email,
                                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date min_Date,
                                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date max_Date) {

        ActivitiesReportByUserResponse myReport = reportService.getReportActivities(projectID, email, min_Date, max_Date);
        return ResponseEntity.ok(myReport);
    }

    @GetMapping("/Reports/timeReport")
    public ResponseEntity<TimeReportResponse> getTimeReport(@RequestParam(required = false) String projectID,
                                                            @RequestParam(required = false) String email,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date min_Date,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date max_Date) {

        TimeReportResponse myReport = reportService.getTimeReport(projectID, email, min_Date, max_Date);
        return ResponseEntity.ok(myReport);
    }

    @GetMapping("/Reports/cumulativeReport")
    public ResponseEntity<CumulativeReportResponse> getCumulativeReport(@RequestParam(required = false) String email,
                                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date min_Date,
                                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date max_Date) {
        CumulativeReportResponse myReport = reportService.getCumulativeReport(email, min_Date, max_Date);
        return ResponseEntity.ok(myReport);
    }

    @GetMapping("/Reports/categorytimeReport")
    public ResponseEntity<CategoriesTimeReportResponse> getCatTimeReport(@RequestParam(required = false) String projectID,
                                                            @RequestParam(required = false) String email,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date min_Date,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date max_Date) {

        CategoriesTimeReportResponse myReport = categoryService.getTimeReport(projectID, email, min_Date, max_Date);
        return ResponseEntity.ok(myReport);
    }


    @GetMapping("/examReport")
    public ResponseEntity<CurrentActivityReport> getExamReport(@RequestParam(required = false) String email) {
        CurrentActivityReport myReport = reportService.getExamReport(email);
        return ResponseEntity.ok(myReport);
    }


}
