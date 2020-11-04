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

    @Autowired
    BugTrackingService bugTrackingService;

    //add activity
    @PostMapping("/activity")
    public ResponseEntity<?> addReport(@RequestBody Report report,
                                       UriComponentsBuilder ucBuilder,
                                       @RequestHeader String Token) {
        Boolean result = activityService.CreateActivity(report, Token);
        if (result) return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //Delete activity
    @DeleteMapping("/activity")
    public ResponseEntity<?> deleteActivity(@RequestParam Integer activity_id,
                                            @RequestHeader String Token) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        if (activityService.DeleteActivity(activity_id, UserName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


    @GetMapping("/activity")
    public ResponseEntity<Report> getActivities(@RequestParam String email, @RequestHeader String Token) {
        Report myReport = activityService.getActivitiesByEmail(email, Token);
        return ResponseEntity.ok(myReport);
    }

    //add process report
    @PostMapping("/process")
    public ResponseEntity<?> addProcessReport(@RequestBody AddProcessReportRequest report,
                                              UriComponentsBuilder ucBuilder,
                                              @RequestHeader String Token) {
        Boolean result = processService.CreateProcessReport(report, Token);
        if (result) return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
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


    @PutMapping("/Bug")
    public ResponseEntity<BugTrackingRequest> UpdateBug(@RequestBody BugTrackingRequest bug, @RequestHeader(required = false) String Token) {

        if (bug == null || bug.getTitle() == null || bug.getTrace() == null)
            throw new ValidationException("Not enough data provided");

        if(!bugTrackingService.updateBug(bug, Token)){
            throw new ValidationException("Bug was not updated");
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/Bug")
    public ResponseEntity<BugTrackingRequest> CreateBug(@RequestBody BugTrackingRequest bug, @RequestHeader(required = false) String Token) {

        if (bug == null || bug.getTitle() == null || bug.getTrace() == null)
            throw new ValidationException("Not enough data provided");


        if(!bugTrackingService.createBug(bug,Token)){
            throw new ValidationException("Bug was not created");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/Bug")
    public ResponseEntity<BugTrackingRequest> getBugById(@RequestParam Integer bugId, @RequestHeader(required = false) String Token) {
        if (bugId !=null) {
            BugTrackingRequest bug = bugTrackingService.findBugById(bugId, Token);
            return ResponseEntity.ok(bug);
        } else
            throw new ValidationException("Not enough data provided");
    }

    @GetMapping("/Bug/all")
    public ResponseEntity<BugTrackingListRequest> getBugsByParameters(@RequestParam(required = false) Integer status,
                                                                      @RequestParam(required = false) String creationdateFrom,
                                                                      @RequestParam(required = false) String creationdateTo,
                                                                      @RequestHeader(required = false) String Token) {

        BugTrackingListRequest bugs = bugTrackingService.findBugsByParameters(status, creationdateFrom, creationdateTo, Token);
        return ResponseEntity.ok(bugs);
    }

    @DeleteMapping("/Bug")
    public ResponseEntity<BugTrackingRequest> deleteBug(@RequestParam Integer bugId, @RequestHeader(required = false) String Token) {

        if (bugId == null)
            throw new ValidationException("Not enough data provided");

        bugTrackingService.deleteBug(bugId,Token);

        return ResponseEntity.ok().build();
    }
}
