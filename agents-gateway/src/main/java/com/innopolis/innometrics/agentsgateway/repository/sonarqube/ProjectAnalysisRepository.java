package com.innopolis.innometrics.agentsgateway.repository.sonarqube;

import com.innopolis.innometrics.agentsgateway.entity.sonarqube.ProjectAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectAnalysisRepository extends JpaRepository<ProjectAnalysis,Long> {

    ProjectAnalysis findByProjectid(Long projectID);
}
