package com.innopolis.innometrics.authserver.DTO;

import java.io.Serializable;
import java.util.Date;

public class UserRequest implements Serializable {

    private String email;

    private String password;

    private String name;

    private String surname;

    private Date confirmed_at;

    private String isactive;

    private String role;


    public UserRequest(String email, String password, String name, String surname, Date confirmed_at, String isactive, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.confirmed_at = confirmed_at;
        this.isactive = isactive;
        this.role=role;
    }

    public UserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getConfirmed_at() {
        return confirmed_at;
    }

    public void setConfirmed_at(Date confirmed_at) {
        this.confirmed_at = confirmed_at;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
