package com.innopolis.innometrics.reportscheduler.scheduler;

import com.innopolis.innometrics.reportscheduler.config.QuartzConfig;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.*;

import javax.sql.DataSource;

import javax.annotation.PostConstruct;
import java.text.ParseException;

@Configuration
@EnableAutoConfiguration
//@EnableConfigurationProperties
//@PropertySource("application.properties")
//@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='true'")
public class SpringQrtzScheduler {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        logger.info("Hello world from Spring...");
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        logger.info("springBeanJobFactory");
        QuartzConfig jobFactory = new QuartzConfig();
        logger.debug("Configuring Job factory");

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job, DataSource quartzDataSource) {
        logger.info("scheduler");
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));

        logger.debug("Setting the Scheduler up");
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);

        // Comment the following line to use the default Quartz job store.
        schedulerFactory.setDataSource(quartzDataSource);

        return schedulerFactory;
    }

    @Bean
    public JobDetailFactoryBean jobDetail() {
        logger.info("jobDetail");
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SampleJob.class);
        jobDetailFactory.setName("Qrtz_Job_Detail");
        jobDetailFactory.setDescription("Invoke Sample Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean trigger(JobDetail job) throws ParseException {
        logger.info("trigger");
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setStartDelay(3000);
        trigger.setName("Qrtz_Trigger");
        trigger.setGroup("test");
        trigger.setCronExpression("0 30 14 ? * MON-FRI *");
        //trigger.setCronExpression("0 * * * * ?");


        return trigger;
    }

    /*
    @Bean
    public SimpleTriggerFactoryBean trigger(JobDetail job) {
        logger.info("trigger");
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);

        int frequencyInSec = 10;
        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);
        trigger.setRepeatInterval(frequencyInSec * 1000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setName("Qrtz_Trigger");

        return trigger;
    }
    */

    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource quartzDataSource() {
        logger.info("quartzDataSource");
        return DataSourceBuilder.create().build();
    }

}
