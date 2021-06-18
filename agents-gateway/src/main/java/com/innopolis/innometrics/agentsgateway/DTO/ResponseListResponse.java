package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class ResponseListResponse {
    private List<ResponseConfigDTO> responseList;

    public ResponseListResponse() {
        this.responseList = new ArrayList<>();
    }

    public List<ResponseConfigDTO> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<ResponseConfigDTO> responseList) {
        this.responseList = responseList;
    }

    public void add(ResponseConfigDTO response) {
        this.responseList.add(response);
    }
}
