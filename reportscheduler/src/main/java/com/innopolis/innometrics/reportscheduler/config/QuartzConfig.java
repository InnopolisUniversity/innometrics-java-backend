package com.innopolis.innometrics.reportscheduler.config;

import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public final class QuartzConfig extends SpringBeanJobFactory implements ApplicationContextAware {
    Logger logger = LoggerFactory.getLogger(getClass());
    private transient AutowireCapableBeanFactory beanFactory;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        logger.info("setApplicationContext");
        beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        logger.info("createJobInstance");
        final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean((job));
        return job;
    }
}
