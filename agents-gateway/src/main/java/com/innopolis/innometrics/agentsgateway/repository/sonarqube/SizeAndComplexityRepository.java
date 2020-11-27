package com.innopolis.innometrics.agentsgateway.repository.sonarqube;

import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Coverage;
import com.innopolis.innometrics.agentsgateway.entity.sonarqube.Sizeandcomplexity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeAndComplexityRepository extends JpaRepository<Sizeandcomplexity,Long> {

    List<Sizeandcomplexity> findAllByAnalysisid(Long analysisId);
}
