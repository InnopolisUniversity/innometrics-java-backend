package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;
import java.util.List;

public class UserResponse implements Serializable {
    private String email;

    private String name;

    private String surname;

    private String isactive;

    private String role;

    private List<PageResponse> pages;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public UserResponse(String email, String name, String surname, String isactive, String role, List<PageResponse> pages) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.isactive = isactive;
        this.role=role;
        this.pages=pages;
    }

    public UserResponse() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PageResponse> getPages() {
        return pages;
    }

    public void setPages(List<PageResponse> pages) {
        this.pages = pages;
    }
}
