package com.innopolis.innometrics.activitiescollector.DTO;

import java.io.Serializable;

public class DeleteRequest implements Serializable {
    private Integer[] ids;

    public DeleteRequest() {
    }

    public DeleteRequest(Integer[] ids) {
        this.ids = ids;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }
}
