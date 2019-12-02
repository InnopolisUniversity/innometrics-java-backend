package com.innopolis.innometrics.restapi.controller;

import com.innopolis.innometrics.restapi.DTO.ActivityReport;
import com.innopolis.innometrics.restapi.DTO.Report;
import com.innopolis.innometrics.restapi.config.JwtToken;
import com.innopolis.innometrics.restapi.service.ActivityService;
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
@CrossOrigin
@RequestMapping(value = "/V1", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataCollectorsAPI {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private JwtToken jwtTokenUtil;

    @Autowired
    ActivityService activityService;

    //@PostMapping(name = "/MeasurementType", produces = "application/json")

    //Project activities
    @GetMapping("/project/{ProjectName}/activity")
    public ResponseEntity<List<Report>> getActivitiesByProject(@PathVariable String ProjectName,
                                                               @RequestParam Integer offset,
                                                               @RequestParam Integer amount_to_return,
                                                               @RequestParam String filters,
                                                               @RequestParam Date start_time,
                                                               @RequestParam Date end_time,
                                                               @RequestHeader String Token) {

        //SeachActivitiesDTO filter = new SeachActivitiesDTO(offset, amount_to_return, filters, start_time, end_time, Token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

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
    @DeleteMapping("/activity")
    public ResponseEntity<?> deleteActivity(@RequestParam Integer activity_id,
                                            @RequestHeader String Token) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        if (activityService.DeleteActivity(activity_id, UserName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    //find activity
    @GetMapping("/activity")
    public ResponseEntity<List<Report>> getActivities(@RequestParam Integer offset,
                                                      @RequestParam Integer amount_to_return,
                                                      @RequestParam String filters,
                                                      @RequestParam Date start_time,
                                                      @RequestParam Date end_time,
                                                      @RequestHeader String Token) {

        //SeachActivitiesDTO filter = new SeachActivitiesDTO(offset, amount_to_return, filters, start_time, end_time, Token);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
