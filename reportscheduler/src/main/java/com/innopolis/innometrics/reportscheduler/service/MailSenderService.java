package com.innopolis.innometrics.reportscheduler.service;

import com.innopolis.innometrics.reportscheduler.feign.MailSenderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService {

    @Autowired
    MailSenderClient mailSenderClient;

    public void sendNotification(){
        System.out.println("Schedule!");
        mailSenderClient.run();

    }
}
