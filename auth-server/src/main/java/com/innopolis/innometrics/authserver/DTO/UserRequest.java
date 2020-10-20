package com.innopolis.innometrics.authserver.DTO;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

public class UserRequest implements Serializable {

    private String email;

    private String password;

    private String name;

    private String surname;

    private String birthday;

    private String gender;

    private String facebook_alias;

    private String telegram_alias;

    private String twitter_alias;

    private String linkedin_alias;

    private Date confirmed_at;

    private String isactive;

    private String role;


    public UserRequest(String email, String password, String name, String surname, String birthday, String gender, String facebook_alias, String telegram_alias, String twitter_alias, String linkedin_alias, Date confirmed_at, String isactive, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.gender = gender;
        this.facebook_alias = facebook_alias;
        this.telegram_alias = telegram_alias;
        this.twitter_alias = twitter_alias;
        this.linkedin_alias = linkedin_alias;
        this.confirmed_at = confirmed_at;
        this.isactive = isactive;
        this.role = role;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFacebook_alias() {
        return facebook_alias;
    }

    public void setFacebook_alias(String facebook_alias) {
        this.facebook_alias = facebook_alias;
    }

    public String getTelegram_alias() {
        return telegram_alias;
    }

    public void setTelegram_alias(String telegram_alias) {
        this.telegram_alias = telegram_alias;
    }

    public String getTwitter_alias() {
        return twitter_alias;
    }

    public void setTwitter_alias(String twitter_alias) {
        this.twitter_alias = twitter_alias;
    }

    public String getLinkedin_alias() {
        return linkedin_alias;
    }

    public void setLinkedin_alias(String linkedin_alias) {
        this.linkedin_alias = linkedin_alias;
    }
}
