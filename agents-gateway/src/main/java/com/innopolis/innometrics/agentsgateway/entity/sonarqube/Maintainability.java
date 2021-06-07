package com.innopolis.innometrics.agentsgateway.entity.sonarqube;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "maintainability", schema = "sonarqube")
@Data
public class Maintainability implements Metric {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "code_smells")
    private String codeSmells;

    @Column(name = "new_code_smells")
    private String newCodeSmells;

    @Column(name = "new_technical_debt")
    private String newTechnicalDebt;

    @Column(name = "sqale_rating")
    private String sqaleRating;

    @Column(name = "analysisid")
    private Long analysisid;

}
