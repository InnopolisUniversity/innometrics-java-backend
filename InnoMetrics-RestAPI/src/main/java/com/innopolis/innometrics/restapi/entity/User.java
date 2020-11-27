package com.innopolis.innometrics.restapi.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class User implements Serializable {

    @Id
    @Column(updatable = false)
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "confirmed_at", insertable = false, updatable = false)
    private Date confirmed_at;

    @Column
    private String birthday;

    @Column
    private String gender;

    @Column
    private String facebook_alias;

    @Column
    private String telegram_alias;

    @Column
    private String twitter_alias;

    @Column
    private String linkedin_alias;

    @Column
    private String isactive;

    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @Column(name = "updateby", insertable = false)
    private String updateby;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "role" , referencedColumnName = "name", insertable = false, updatable = false)
    private Role role;

    public User() {
    }

//    public User(String email, String password, String name, String surname, Date confirmed_at, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby, Role role) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.surname = surname;
//        this.confirmed_at = confirmed_at;
//        this.isactive = isactive;
//        this.creationdate = creationdate;
//        this.createdby = createdby;
//        this.lastupdate = lastupdate;
//        this.updateby = updateby;
//        this.role = role;
//    }
//
//    public User(String email, String password, String name, String surname, Date confirmed_at, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.surname = surname;
//        this.confirmed_at = confirmed_at;
//        this.isactive = isactive;
//        this.creationdate = creationdate;
//        this.createdby = createdby;
//        this.lastupdate = lastupdate;
//        this.updateby = updateby;
//    }



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

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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

    @PreUpdate
    public void preUpdate(){
        this.lastupdate = new Date();
    }
}
