package com.innopolis.innometrics.authserver.DTO;

import java.util.ArrayList;
import java.util.List;

public class PageListResponse {
    List<PageResponse> pageList;

    public PageListResponse() {
        pageList = new ArrayList<>();
    }

    public PageListResponse(List<PageResponse> pageList) {
        this.pageList = pageList;
    }

    public List<PageResponse> getPageList() {
        return pageList;
    }

    public void setPageList(List<PageResponse> pageList) {
        this.pageList = pageList;
    }

    public void addPageResponse(PageResponse pageResponse){
        this.pageList.add(pageResponse);
    }
}
