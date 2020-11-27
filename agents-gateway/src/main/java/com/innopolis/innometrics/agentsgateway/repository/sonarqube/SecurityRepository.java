package com.innopolis.innometrics.agentsgateway.repository.sonarqube;

import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Coverage;
import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Security;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityRepository extends JpaRepository<Security,Long> {

    List<Security> findAllByAnalysisid(Long analysisId);
}
