package com.innopolis.innometrics.agentsgateway.entity.sonarqube;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "issues", schema = "sonarqube")
@Data
public class Issues implements Metric {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "blocker_violations")
    private String blockerViolations;

    @Column(name = "major_violations")
    private String majorViolations;

    @Column(name = "minor_violations")
    private String minorViolations;

    @Column(name = "new_blocker_violations")
    private String newBlockerViolations;

    @Column(name = "new_critical_violations")
    private String newCriticalViolations;

    @Column(name = "new_major_violations")
    private String newMajorViolations;

    @Column(name = "new_minor_violations")
    private String newMinorViolations;

    @Column(name = "reopened_issues")
    private String reopenedIssues;

    @Column(name = "violations")
    private String violations;

    @Column(name = "analysisid")
    private Long analysisid;

}
