package com.innopolis.innometrics.authserver.DTO;

import java.io.Serializable;
import java.util.List;

public class UserResponse implements Serializable {
    private String email;

    private String name;

    private String surname;

    private String birthday;

    private String gender;

    private String facebook_alias;

    private String telegram_alias;

    private String twitter_alias;

    private String linkedin_alias;

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

    public UserResponse(String email, String name, String surname, String birthday, String gender, String facebook_alias, String telegram_alias, String twitter_alias, String linkedin_alias, String isactive, String role, List<PageResponse> pages) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.gender = gender;
        this.facebook_alias = facebook_alias;
        this.telegram_alias = telegram_alias;
        this.twitter_alias = twitter_alias;
        this.linkedin_alias = linkedin_alias;
        this.isactive = isactive;
        this.role = role;
        this.pages = pages;
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
