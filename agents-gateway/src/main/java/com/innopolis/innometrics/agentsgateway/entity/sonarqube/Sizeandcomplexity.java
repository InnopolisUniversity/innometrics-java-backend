package com.innopolis.innometrics.agentsgateway.entity.sonarqube;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sizeandcomplexity", schema = "sonarqube")
@Data
public class Sizeandcomplexity implements Metric {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "classes")
    private String classes;

    @Column(name = "complexity_in_classes")
    private String complexityInClasses;

    @Column(name = "lines")
    private String lines;

    @Column(name = "ncloc")
    private String ncloc;

    @Column(name = "analysisid")
    private Long analysisid;
}
