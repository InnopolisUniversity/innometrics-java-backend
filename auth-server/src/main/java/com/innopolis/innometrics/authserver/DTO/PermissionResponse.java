package com.innopolis.innometrics.authserver.DTO;

import com.innopolis.innometrics.authserver.entitiy.Page;

import java.util.ArrayList;
import java.util.List;

public class PermissionResponse {
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
