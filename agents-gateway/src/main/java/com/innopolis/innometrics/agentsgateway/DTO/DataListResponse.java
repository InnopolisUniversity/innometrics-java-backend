package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class DataListResponse {
    private List<DataConfigDTO> dataList;

    public DataListResponse() {
        this.dataList = new ArrayList<>();
    }

    public List<DataConfigDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataConfigDTO> dataList) {
        this.dataList = dataList;
    }

    public void add(DataConfigDTO data) {
        this.dataList.add(data);
    }
}
