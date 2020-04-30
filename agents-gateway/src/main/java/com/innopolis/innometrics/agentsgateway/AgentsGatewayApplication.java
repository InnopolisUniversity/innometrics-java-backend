package com.innopolis.innometrics.agentsgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;

@SpringBootApplication
//@EnableCircuitBreaker
public class AgentsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentsGatewayApplication.class, args);
    }

    //@LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
