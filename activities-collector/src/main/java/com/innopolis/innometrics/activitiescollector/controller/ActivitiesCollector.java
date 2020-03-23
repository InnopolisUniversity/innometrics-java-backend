package com.innopolis.innometrics.activitiescollector.controller;


import com.innopolis.innometrics.activitiescollector.DTO.ActivityReport;
import com.innopolis.innometrics.activitiescollector.DTO.Report;
import com.innopolis.innometrics.activitiescollector.config.JwtToken;
import com.innopolis.innometrics.activitiescollector.service.ActivityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping(value = "/V1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivitiesCollector {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    ActivityService activityService;

    @Autowired
    private JwtToken jwtTokenUtil;

    //add activity
    @PostMapping("/activity")
    public ResponseEntity<?> addReport(@RequestBody Report report,
                                       UriComponentsBuilder ucBuilder,
                                       @RequestHeader String Token) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        Date CreationDate = new Date();
        for (ActivityReport activity : report.getActivities()) {
            activityService.CreateActivty(activity, UserName, CreationDate);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Delete activity
    @DeleteMapping("/activity/{activity_id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer activity_id,
                                            @RequestHeader String Token) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        if (activityService.DeleteActivity(activity_id, UserName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/activity/{email}")
    public ResponseEntity<Report> getActivities(@PathVariable String email, @RequestHeader String Token) {
        Report myReport = activityService.getActivitiesByEmail(email);
        return ResponseEntity.ok(myReport);
    }

    //Project activities
    //@GetMapping("/activity/{ProjectName}")
    //public ResponseEntity<Report> getActivitiesByProject(@PathVariable String ProjectName, @RequestHeader String Token) {
    //    return new ResponseEntity<>(HttpStatus.OK);
    //}

}
