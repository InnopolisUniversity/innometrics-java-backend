package com.innopolis.innometrics.agentsgateway.DTO;

import java.util.ArrayList;
import java.util.List;

public class DetailsListResponse {
    private List<DetailsConfigDTO> detailsList;

    public DetailsListResponse() {
        this.detailsList = new ArrayList<>();
    }

    public List<DetailsConfigDTO> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<DetailsConfigDTO> detailsList) {
        this.detailsList = detailsList;
    }

    public void add(DetailsConfigDTO details) {
        this.detailsList.add(details);
    }
}
