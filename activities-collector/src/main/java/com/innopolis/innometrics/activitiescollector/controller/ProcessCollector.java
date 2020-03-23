package com.innopolis.innometrics.activitiescollector.controller;


import com.innopolis.innometrics.activitiescollector.DTO.*;
import com.innopolis.innometrics.activitiescollector.config.JwtToken;
import com.innopolis.innometrics.activitiescollector.service.ProcessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;

@RestController
@RequestMapping(value = "/V1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessCollector {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    ProcessService processService;

    @Autowired
    private JwtToken jwtTokenUtil;

    @PostMapping("/process")
    public ResponseEntity<?> addProcessReport(@RequestBody AddProcessReportRequest report,
                                              UriComponentsBuilder ucBuilder,
                                              @RequestHeader String Token) {

        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        Date CreationDate = new Date();

        for (ProcessReport activity : report.getProcessesReport()) {
            processService.CreateProcessReport(activity, UserName, CreationDate);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/process/{email}")
    public ResponseEntity<ProcessReportResponse> getProcessReportByEmail(@PathVariable String email, @RequestHeader String Token) {
        ProcessReportResponse myReport = processService.getActivitiesByEmail(email);
        return ResponseEntity.ok(myReport);
    }
}
