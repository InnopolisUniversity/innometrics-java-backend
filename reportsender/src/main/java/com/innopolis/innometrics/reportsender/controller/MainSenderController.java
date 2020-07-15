package com.innopolis.innometrics.reportsender.controller;

import com.innopolis.innometrics.reportsender.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainSenderController {

    @Autowired
    MailSenderService mailSenderService;

    @GetMapping(path = "/run")
    public void run(){
        mailSenderService.sendNotificationToAdmins();
    }
}
