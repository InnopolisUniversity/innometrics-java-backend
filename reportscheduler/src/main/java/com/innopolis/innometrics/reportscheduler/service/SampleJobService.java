package com.innopolis.innometrics.reportscheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SampleJobService {
    public static final long EXECUTION_TIME = 5000L;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //private AtomicInteger count = new AtomicInteger();
    @Autowired
    MailSenderService mailSenderService;

    public void executeSampleJob() {

        logger.info("The sample job has begun...");
        try {
            //Thread.sleep(EXECUTION_TIME);
            //count.incrementAndGet();
            mailSenderService.sendNotification();
        } catch (Exception e) {
            logger.error("Error while executing sample job", e);
        } finally {

            logger.info("Sample job has finished...");
        }
    }

    //public int getNumberOfInvocations() {
    //    return count.get();
    //}
}
