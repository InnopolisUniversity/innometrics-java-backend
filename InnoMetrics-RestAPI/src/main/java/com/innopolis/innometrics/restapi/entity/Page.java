package com.innopolis.innometrics.restapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Page {

    @Id
    @Column
    private String page;

    @Column
    private String icon;

    public Page(){}

    public Page(String page, String icon) {
        this.page = page;
        this.icon = icon;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
