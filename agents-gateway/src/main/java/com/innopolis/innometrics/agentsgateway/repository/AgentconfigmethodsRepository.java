package com.innopolis.innometrics.agentsgateway.repository;

import com.innopolis.innometrics.agentsgateway.entity.Agentconfigmethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentconfigmethodsRepository  extends JpaRepository<Agentconfigmethods, Integer> {
    List<Agentconfigmethods> findByAgentid(Integer agentId);

    Agentconfigmethods findByAgentidAndOperation(Integer agentId, String operation);

}
