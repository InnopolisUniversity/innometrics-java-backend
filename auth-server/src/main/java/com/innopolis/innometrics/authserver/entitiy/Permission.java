package com.innopolis.innometrics.authserver.entitiy;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Permission {
    @Id
     private String page;

    @Column
    private String role;

    public Permission(String page, String role) {
        this.page = page;
        this.role = role;
    }

    public Permission() {
    }

    public String getPage() {
        return page;
    }

    public String getRole() {
        return role;
    }
}
