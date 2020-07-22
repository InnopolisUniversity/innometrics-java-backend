package com.innopolis.innometrics.authserver.DTO;

import com.innopolis.innometrics.authserver.entitiy.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserResponse implements Serializable {
    private String email;

    private String name;

    private String surname;

    private String isactive;

    private Role role;

//    private List<String> permissions;
//
//    public void setPermissions(List<String> permissions) {
//        this.permissions = permissions;
//    }

//    public List<String> getPermissions() {
//        return permissions;
//    }

    public Role getRole() {
        return role;
    }

//    public List<String> getPermissions(){
//        return role.getPermissions();
//    }

    public void setRole(Role role) {
        this.role = role;
    }

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

    public UserResponse(String email, String name, String surname, String isactive) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.isactive = isactive;
    }

    public UserResponse(String email, String name, String surname, String isactive, Role role) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.isactive = isactive;
        this.role = role;
    }

    public UserResponse() {
    }
}
