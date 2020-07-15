package com.innopolis.innometrics.agentsgateway.repository;

import com.innopolis.innometrics.agentsgateway.DTO.IAgentStatus;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AgentconfigRepository extends JpaRepository<Agentconfig, Integer> {
    Agentconfig findByAgentname(String agentName);
    Agentconfig findByAgentid(Integer agentid);


    @Modifying
    @Query( value ="select distinct agent.agentid,\n" +
            "       agent.agentname,\n" +
            "       agent.description,\n" +
            "       case when project.configid is not null then 'Y' else 'N' end isconnected\n" +
            "from innometricsagents.agentconfig agent\n" +
            "left outer join innometricsagents.agents_x_project project\n" +
            "  on agent.agentid = project.agentid\n" +
            "  and cast(project.projectid  as text) = cast(:ProjectID as text);", nativeQuery = true)
    List<IAgentStatus> getAgentList(@Param("ProjectID") Integer projectID);
}
