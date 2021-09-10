package com.innopolis.innometrics.agentsgateway.entity.sonarqube;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sonar_provider", schema = "sonarqube")
@Data
public class SonarProvider {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "hostref")
    private String hostref;

    @Column(name = "internaltoken")
    private String internaltoken;

    @Column(name = "is_hook_created")
    private Boolean isHookCreated;

    @Column(name = "providername")
    private String providername;

    @Column(name = "token")
    private String token;
}
