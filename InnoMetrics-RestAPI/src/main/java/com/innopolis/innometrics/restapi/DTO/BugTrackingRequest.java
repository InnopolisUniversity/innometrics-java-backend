package com.innopolis.innometrics.restapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugTrackingRequest implements Serializable {

    private Integer bugId;

    private String username;

    private String title;

    private String classOfBug;

    private Integer line;

    private String comment;

    private String trace;

    private String os;

    private String dataCollectorVersion;

    private Timestamp creationdate;

    private Timestamp lastupdate;

    // 0 - not solved, 1 - solved
    private boolean status;

}
