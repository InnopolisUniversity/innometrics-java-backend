package com.innopolis.innometrics.activitiescollector.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse implements Serializable {
    private Integer catid;
    private String catname;
    private String isactive;
    private Date creationdate;
    private String createdby;
    private Date lastupdate;
    private String updateby;
}
