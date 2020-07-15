package com.innopolis.innometrics.reportscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReportschedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportschedulerApplication.class, args);
    }

}
