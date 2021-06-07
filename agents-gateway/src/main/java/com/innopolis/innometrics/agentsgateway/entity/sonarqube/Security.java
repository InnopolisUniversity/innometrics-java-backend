package com.innopolis.innometrics.agentsgateway.entity.sonarqube;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "security", schema = "sonarqube")
@Data
public class Security implements Metric {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "new_security_hotspots")
    private String newSecurityHotspots;

    @Column(name = "new_security_hotspots_reviewed")
    private String newSecurityHotspotsReviewed;

    @Column(name = "new_vulnerabilities")
    private String newVulnerabilities;

    @Column(name = "security_hotspots")
    private String securityHotspots;

    @Column(name = "security_rating")
    private String securityRating;

    @Column(name = "analysisid")
    private Long analysisid;
}
