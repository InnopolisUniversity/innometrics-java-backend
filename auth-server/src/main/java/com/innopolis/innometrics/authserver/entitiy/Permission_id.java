package com.innopolis.innometrics.authserver.entitiy;

import java.io.Serializable;

public class Permission_id implements Serializable {

    private String page;

    private String role;

    public Permission_id(String page, String role) {
        this.page = page;
        this.role = role;
    }

    public Permission_id() {
    }

}
