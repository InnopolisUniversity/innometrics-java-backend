package com.innopolis.innometrics.agentsgateway.entity.sonarqube;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "reliability", schema = "sonarqube")
@Data
public class Reliability implements Metric {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "alert_status")
    private String alertStatus;

    @Column(name = "bugs")
    private String bugs;

    @Column(name = "new_bugs")
    private String newBugs;

    @Column(name = "reliability_rating")
    private String reliabilityRating;

    @Column(name = "analysisid")
    private Long analysisid;
}
