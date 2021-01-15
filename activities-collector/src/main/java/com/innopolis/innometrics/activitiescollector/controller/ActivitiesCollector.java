package com.innopolis.innometrics.activitiescollector.controller;


import com.innopolis.innometrics.activitiescollector.DTO.ActivitiesReportResponse;
import com.innopolis.innometrics.activitiescollector.DTO.ActivityReport;
import com.innopolis.innometrics.activitiescollector.DTO.DeleteRequest;
import com.innopolis.innometrics.activitiescollector.DTO.Report;
import com.innopolis.innometrics.activitiescollector.config.JwtToken;
import com.innopolis.innometrics.activitiescollector.entity.Activity;
import com.innopolis.innometrics.activitiescollector.service.ActivityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin
@RequestMapping(value = "/V1/activity", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivitiesCollector {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    ActivityService activityService;

    @Autowired
    private JwtToken jwtTokenUtil;

    @Autowired
    private ModelMapper modelMapper;

    //add activity
    @PostMapping("/")
    public ResponseEntity<?> addReport(@RequestBody Report report,
                                       UriComponentsBuilder ucBuilder,
                                       @RequestHeader String Token) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        Date CreationDate = new Date();
        LOG.info("a request received from " + UserName + ", with " + report.getActivities().size() + " activities");
        if(report.getActivities().size() > 1000){
            LOG.info("");
            return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
        }
        for (ActivityReport activity : report.getActivities()) {
            activityService.CreateActivty(activity, UserName, CreationDate);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Delete activity
    @DeleteMapping("/{activity_id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer activity_id,
                                            @RequestHeader String Token) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        if (activityService.DeleteActivity(activity_id, UserName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/")
    @ResponseBody
    public ActivitiesReportResponse getActivities(@RequestHeader String Token,
                                                  @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date reportDate,
                                                  UriComponentsBuilder uriBuilder,
                                                  HttpServletResponse response) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        ActivitiesReportResponse myReport = activityService.getActivitiesByEmailandDay(UserName, reportDate);

        return myReport;
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteActivitiesWithIds(@RequestBody DeleteRequest request,
                                                     @RequestHeader String Token) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        if (activityService.DeleteActivitiesWithIds(request.getIds())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    //Project activities
    //@GetMapping("/activity/{ProjectName}")
    //public ResponseEntity<Report> getActivitiesByProject(@PathVariable String ProjectName, @RequestHeader String Token) {
    //    return new ResponseEntity<>(HttpStatus.OK);
    //}

}
