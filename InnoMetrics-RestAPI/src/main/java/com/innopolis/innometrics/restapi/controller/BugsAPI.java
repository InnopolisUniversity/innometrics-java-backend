package com.innopolis.innometrics.restapi.controller;

import com.innopolis.innometrics.restapi.DTO.BugReportRequest;
import com.innopolis.innometrics.restapi.DTO.BugTrackingListRequest;
import com.innopolis.innometrics.restapi.DTO.BugTrackingRequest;
import com.innopolis.innometrics.restapi.exceptions.ValidationException;
import com.innopolis.innometrics.restapi.service.BugTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/V1/Bug", produces = MediaType.APPLICATION_JSON_VALUE)
public class BugsAPI {

    @Autowired
    BugTrackingService bugTrackingService;

    @PutMapping("/")
    public ResponseEntity<BugTrackingRequest> UpdateBug(@RequestBody BugTrackingRequest bug, @RequestHeader(required = false) String Token) {

        if (bug == null || bug.getTitle() == null || bug.getTrace() == null)
            throw new ValidationException("Not enough data provided");

        if(!bugTrackingService.updateBug(bug, Token)){
            throw new ValidationException("Bug was not updated");
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/")
    public ResponseEntity<BugTrackingRequest> CreateBug(@RequestBody BugReportRequest bug, @RequestHeader(required = false) String Token) {

        if (bug == null || bug.getTitle() == null || bug.getTrace() == null)
            throw new ValidationException("Not enough data provided");


        if(!bugTrackingService.createBug(bug,Token)){
            throw new ValidationException("Bug was not created");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<BugTrackingRequest> getBugById(@RequestParam Integer bugId, @RequestHeader(required = false) String Token) {
        if (bugId !=null) {
            BugTrackingRequest bug = bugTrackingService.findBugById(bugId, Token);
            return ResponseEntity.ok(bug);
        } else
            throw new ValidationException("Not enough data provided");
    }

    @GetMapping("/all")
    public ResponseEntity<BugTrackingListRequest> getBugsByParameters(@RequestParam(required = false) Integer status,
                                                                      @RequestParam(required = false) String creationdateFrom,
                                                                      @RequestParam(required = false) String creationdateTo,
                                                                      @RequestHeader(required = false) String Token) {

        BugTrackingListRequest bugs = bugTrackingService.findBugsByParameters(status, creationdateFrom, creationdateTo, Token);
        return ResponseEntity.ok(bugs);
    }

    @DeleteMapping("/")
    public ResponseEntity<BugTrackingRequest> deleteBug(@RequestParam Integer bugId, @RequestHeader(required = false) String Token) {

        if (bugId == null)
            throw new ValidationException("Not enough data provided");

        bugTrackingService.deleteBug(bugId,Token);

        return ResponseEntity.ok().build();
    }
}
