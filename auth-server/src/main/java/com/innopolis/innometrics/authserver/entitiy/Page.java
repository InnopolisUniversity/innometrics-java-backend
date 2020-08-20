package com.innopolis.innometrics.authserver.entitiy;

import javax.persistence.*;

@Entity
@Table
public class Page {

    @Id
    @Column
    private String page;

    @Column
    private String icon;

    Page(){}

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
