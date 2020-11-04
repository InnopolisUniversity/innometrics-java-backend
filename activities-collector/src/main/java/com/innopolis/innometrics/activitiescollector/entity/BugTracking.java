package com.innopolis.innometrics.activitiescollector.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "bug_tracking")
public class BugTracking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bugId;

    @Column
    private String username;

    @Column
    private String title;

    @Column(name = "class")
    private String classOfBug;

    @Column
    private Integer line;

    @Column
    private String comment;

    @Column
    private String trace;

    @Column
    private String os;

    @Column
    private String dataCollectorVersion;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Timestamp creationdate;

    @Column(name = "lastupdate")
    private Timestamp lastupdate;

    // 0 - not solved, 1 - solved
    @Column
    private boolean status;

}
