package com.innopolis.innometrics.restapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugReportRequest implements Serializable {
    private String username;
    private String title;
    private String classOfBug;
    private Integer line;
    private String trace;
    private String os;
    private String dataCollectorVersion;
}
