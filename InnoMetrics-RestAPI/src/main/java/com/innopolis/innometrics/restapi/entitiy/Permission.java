package com.innopolis.innometrics.restapi.entitiy;


import javax.persistence.*;

@Entity
@Table
@IdClass(Permission_id.class)
public class Permission {

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "page", referencedColumnName = "page")
    private Page page;

    @Id
    @Column
    private String role;

    public Permission(Page page, String role) {
        this.page = page;
        this.role = role;
    }

    public Permission() {
    }

    public Page getPage() {
        return page;
    }

    public String getRole() {
        return role;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
