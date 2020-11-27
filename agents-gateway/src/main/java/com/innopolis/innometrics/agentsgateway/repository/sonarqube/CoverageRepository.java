package com.innopolis.innometrics.agentsgateway.repository.sonarqube;

import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Coverage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoverageRepository extends JpaRepository<Coverage,Long> {

    List<Coverage> findAllByAnalysisid(Long analysisId);
}
