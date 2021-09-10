package com.innopolis.innometrics.agentsgateway.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SonarProjectMeasures implements Serializable {
    private String metric;
    private String value;
    private String bestValue;


    public SonarProjectMeasures() {

    }
}
