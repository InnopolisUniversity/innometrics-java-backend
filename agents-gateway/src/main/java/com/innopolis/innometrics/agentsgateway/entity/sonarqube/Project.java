package com.innopolis.innometrics.agentsgateway.entity.sonarqube;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "project", schema = "sonarqube")
@Data
public class Project {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "projectid")
    private String projectid;

    @Column(name = "projectname")
    private String projectname;

    @Column(name = "providerid")
    private Long providerid;

}
