package com.innopolis.innometrics.agentsgateway.repository.sonarqube;

import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Coverage;
import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Issues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssuesRepository  extends JpaRepository<Issues,Long> {

    List<Issues> findAllByAnalysisid(Long analysisId);

}
