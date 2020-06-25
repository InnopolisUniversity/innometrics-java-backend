package com.innopolis.innometrics.agentsgateway.repository;

import com.innopolis.innometrics.agentsgateway.entity.Agentsxproject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentsxprojectRepository extends JpaRepository<Agentsxproject, Integer> {
    Agentsxproject findByAgentidAndProjectid(Integer agentid, Integer projectid);
}
