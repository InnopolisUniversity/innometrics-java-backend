package com.innopolis.innometrics.agentsgateway.entity.sonarqube;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "project_analysis", schema = "sonarqube")
@Data
public class ProjectAnalysis {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "analysisid")
    private String analysisid;

    @Column(name = "projectid")
    private Long projectid;
}
