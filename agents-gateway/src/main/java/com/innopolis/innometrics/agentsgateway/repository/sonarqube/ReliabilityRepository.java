package com.innopolis.innometrics.agentsgateway.repository.sonarqube;

import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Coverage;
import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Reliability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReliabilityRepository extends JpaRepository<Reliability,Long> {

    List<Reliability> findAllByAnalysisid(Long analysisId);
}
