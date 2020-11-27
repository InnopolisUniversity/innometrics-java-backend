package com.innopolis.innometrics.agentsgateway.repository.sonarqube;

import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Coverage;
import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Maintainability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintainabilityRepository extends JpaRepository<Maintainability,Long> {

    List<Maintainability> findAllByAnalysisid(Long analysisId);
}
