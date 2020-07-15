package com.innopolis.innometrics.agentsgateway.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectProjectRequest implements Serializable {
    private Integer AgentId;
    private Integer projectID;
    private String RepoReference;
}
