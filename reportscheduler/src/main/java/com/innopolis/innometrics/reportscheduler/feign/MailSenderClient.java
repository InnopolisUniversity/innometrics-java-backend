package com.innopolis.innometrics.reportscheduler.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url ="${mail.sender.host}",
value = "MainSender")
public interface MailSenderClient {
    @GetMapping(path = "/run")
    public void run();
}
