package com.innopolis.innometrics.activitiescollector.controller;


import com.innopolis.innometrics.activitiescollector.DTO.*;
import com.innopolis.innometrics.activitiescollector.config.JwtToken;
import com.innopolis.innometrics.activitiescollector.service.ProcessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/process")
    public ResponseEntity<ProcessDayReportResponse> getProcessReport(@RequestHeader String Token,
                                                                     @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date reportDate,
                                                                     UriComponentsBuilder uriBuilder,
                                                                     HttpServletResponse response) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        ProcessDayReportResponse myReport = processService.getProcessesByEmailAndDay(UserName, reportDate);
        return ResponseEntity.ok(myReport);
    }


    @DeleteMapping("/process/{process_id}")
    public ResponseEntity<?> deleteProcess(@PathVariable Integer process_id,
                                            @RequestHeader String Token) {
        String UserName = jwtTokenUtil.getUsernameFromToken(Token);
        if (processService.DeleteProcess(process_id, UserName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
