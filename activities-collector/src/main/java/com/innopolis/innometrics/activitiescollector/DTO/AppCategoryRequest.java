package com.innopolis.innometrics.activitiescollector.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppCategoryRequest implements Serializable {
    private Integer appid;
    private String appname;
    private Integer catid;
    private String isactive;
}
