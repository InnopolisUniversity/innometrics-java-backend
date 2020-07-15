package com.innopolis.innometrics.reportsender.controller;

import com.innopolis.innometrics.reportsender.service.UnsubscibeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnsubscribeController {

    @Autowired
    public UnsubscibeService unsubscibeService;

    @GetMapping(path = "/unsubscribe/{email}")
    public void run(@PathVariable String email){
        unsubscibeService.setEmailSubscription(email);
    }


}
