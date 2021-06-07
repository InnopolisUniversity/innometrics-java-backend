package com.innopolis.innometrics.agentsgateway.entity.sonarqube;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "coverage", schema = "sonarqube")
@Data
public class Coverage implements Metric {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "branch_coverage")
    private String branchCoverage;

    @Column(name = "coverage")
    private String coverage;

    @Column(name = "line_coverage")
    private String lineCoverage;

    @Column(name = "analysisid")
    private Long analysisid;
}
