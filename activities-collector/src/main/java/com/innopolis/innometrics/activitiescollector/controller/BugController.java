package com.innopolis.innometrics.activitiescollector.controller;

import com.innopolis.innometrics.activitiescollector.DTO.BugTrackingListRequest;
import com.innopolis.innometrics.activitiescollector.DTO.BugTrackingRequest;
import com.innopolis.innometrics.activitiescollector.service.BugTrackingService;
import com.netflix.config.validation.ValidationException;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.Timestamp;

@RestController
@RequestMapping(value = "/V1", produces = MediaType.APPLICATION_JSON_VALUE)
public class BugController {

    @Autowired
    BugTrackingService bugTrackingService;

    @PutMapping("/Bug")
    public ResponseEntity<BugTrackingRequest> UpdateBug(@RequestBody BugTrackingRequest bug, @RequestHeader(required = false) String Token) {

        if (bug == null || bug.getTitle() == null || bug.getTrace() == null)
            throw new ValidationException("Not enough data provided");

        BugTrackingRequest bugTrackingRequest;
        if (bugTrackingService.existsById(bug.getBugId()))
            bugTrackingRequest = bugTrackingService.update(bug);
        else throw new ValidationException("Bug with this id does not exist: "+bug.getBugId());

        if(bugTrackingRequest == null){
            throw new ValidationException("Bug was not updated");
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/Bug")
    public ResponseEntity<BugTrackingRequest> CreateBug(@RequestBody BugTrackingRequest bug, @RequestHeader(required = false) String Token) {

        if (bug == null || bug.getTitle() == null || bug.getTrace() == null)
            throw new ValidationException("Not enough data provided");

        BugTrackingRequest bugTrackingRequest;
        if (bugTrackingService.existsById(bug.getBugId()))
            throw new ValidationException("Bug with this id already exists: "+bug.getBugId());
        else bugTrackingRequest= bugTrackingService.create(bug);

        if(bugTrackingRequest == null){
            throw new ValidationException("Bug was not created");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/Bug")
    public ResponseEntity<BugTrackingRequest> getBugById(@RequestParam Integer bugId, @RequestHeader(required = false) String Token) {
        if (bugId !=null) {
            BugTrackingRequest bug = bugTrackingService.findByID(bugId);

            return ResponseEntity.ok(bug);
        } else
            throw new ValidationException("Not enough data provided");
    }

    @GetMapping("/Bug/all")
    public ResponseEntity<BugTrackingListRequest> getBugsByParameters(@RequestParam(required = false) Integer status,
                                                          @RequestParam(required = false) String creationdateFrom,
                                                          @RequestParam(required = false) String creationdateTo,
                                                          @RequestHeader(required = false) String Token) throws UnsupportedEncodingException {

        String creationdateFrom2 = URLDecoder.decode(creationdateFrom, "UTF-8");
        String creationdateTo2 = URLDecoder.decode(creationdateTo, "UTF-8");
        Timestamp dFrom = new Timestamp(1);
        Timestamp dTo = new Timestamp(System.currentTimeMillis());
        if (creationdateFrom2 != null && !creationdateFrom2.equals(""))
            dFrom = java.sql.Timestamp.valueOf(creationdateFrom2);
        if (creationdateTo2 != null && !creationdateTo2.equals(""))
            dTo = java.sql.Timestamp.valueOf(creationdateTo2);

        BugTrackingListRequest bugs ;
        if (status == null || status > 1 || status < 0) {
            bugs = bugTrackingService.findBugsByCreationDate(dFrom, dTo);
        } else {
            Boolean stat= false;
            if(status.equals(1))
                stat=true;

            bugs = bugTrackingService.findBugsByCreationDateWithStatus(dFrom, dTo, stat);
        }
        return ResponseEntity.ok(bugs);
    }

    @DeleteMapping("/Bug")
    public ResponseEntity<BugTrackingRequest> deleteBug(@RequestParam Integer bugId, @RequestHeader(required = false) String Token) {

        if (bugId == null)
            throw new ValidationException("Not enough data provided");

        bugTrackingService.delete(bugId);

        return ResponseEntity.ok().build();
    }


}
