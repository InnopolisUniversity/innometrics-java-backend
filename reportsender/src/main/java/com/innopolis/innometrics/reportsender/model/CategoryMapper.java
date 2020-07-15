package com.innopolis.innometrics.reportsender.model;

import java.time.Duration;

public class CategoryMapper {

    Duration usedTime;
    String catname;

    CategoryMapper(String catname, Duration usedTime){
        this.catname=catname;
        this.usedTime=usedTime;
    }
}
