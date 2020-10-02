package com.innopolis.innometrics.restapi.DTO;

import java.util.ArrayList;
import java.util.List;

public class CompanyListRequest {
    List<CompanyRequest> companyRequestList;

    public CompanyListRequest() {
        companyRequestList = new ArrayList<>();
    }

    public CompanyListRequest(List<CompanyRequest> companyRequests) {
        this.companyRequestList = companyRequests;
    }

    public List<CompanyRequest> getCompanyRequestList() {
        return companyRequestList;
    }

    public void setCompanyRequestList(List<CompanyRequest> companyRequests) { this.companyRequestList = companyRequests; }

    public void addCompanyRequest(CompanyRequest companyRequest){
        this.companyRequestList.add(companyRequest);
    }
}
