package com.innopolis.innometrics.agentsgateway.repository;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentconfigRepository extends JpaRepository<Agentconfig, Integer> {
    Agentconfig findByAgentname(String agentName);
}
