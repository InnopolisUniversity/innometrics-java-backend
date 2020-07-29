package com.innopolis.innometrics.restapi.DTO;


import com.innopolis.innometrics.restapi.entitiy.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PermissionResponse implements Serializable {
    private List<Page> pages;

    private String role;

    public PermissionResponse(List<Page> pages, String role) {
        this.pages = pages;
        this.role = role;
    }

    public PermissionResponse() {
        pages = new ArrayList<>();
    }

    public List<Page> getPages() {
        return pages;
    }

    public String getRole() {
        return role;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addPage(Page page){
        this.pages.add(page);
    }
}
