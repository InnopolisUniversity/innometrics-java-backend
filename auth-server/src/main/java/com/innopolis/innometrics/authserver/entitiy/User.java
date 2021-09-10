package com.innopolis.innometrics.authserver.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_users",
            joinColumns = @JoinColumn(name = "email"),
            inverseJoinColumns = @JoinColumn(name = "projectid"))
    Set<Project> projects ;

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
    @JoinColumn(nullable = false, name = "role" , referencedColumnName = "name")
    private Role role;

//    public User(String email, String password, String name, String surname, Date confirmed_at, Set<Project> projects, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby, Role role) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.surname = surname;
//        this.confirmed_at = confirmed_at;
//        this.projects = projects;
//        this.isactive = isactive;
//        this.creationdate = creationdate;
//        this.createdby = createdby;
//        this.lastupdate = lastupdate;
//        this.updateby = updateby;
//        this.role = role;
//    }
//
//    public User(String email, String password, String name, String surname, Date confirmed_at, Set<Project> projects, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.surname = surname;
//        this.confirmed_at = confirmed_at;
//        this.projects = projects;
//        this.isactive = isactive;
//        this.creationdate = creationdate;
//        this.createdby = createdby;
//        this.lastupdate = lastupdate;
//        this.updateby = updateby;
//    }
//
//
//
//
//    public User() {
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public Date getConfirmed_at() {
//        return confirmed_at;
//    }
//
//    public void setConfirmed_at(Date confirmed_at) {
//        this.confirmed_at = confirmed_at;
//    }
//
//    public Set<Project> getProjects() {
//        return projects;
//    }
//
//    public void setProjects(Set<Project> projects) {
//        this.projects = projects;
//    }
//
//    public String getIsactive() {
//        return isactive;
//    }
//
//    public void setIsactive(String isactive) {
//        this.isactive = isactive;
//    }
//
//    public Date getCreationdate() {
//        return creationdate;
//    }
//
//    public void setCreationdate(Date creationdate) {
//        this.creationdate = creationdate;
//    }
//
//    public String getCreatedby() {
//        return createdby;
//    }
//
//    public void setCreatedby(String createdby) {
//        this.createdby = createdby;
//    }
//
//    public Date getLastupdate() {
//        return lastupdate;
//    }
//
//    public void setLastupdate(Date lastupdate) {
//        this.lastupdate = lastupdate;
//    }
//
//    public String getUpdateby() {
//        return updateby;
//    }
//
//    public void setUpdateby(String updateby) {
//        this.updateby = updateby;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//







//
    @PreUpdate
    public void preUpdate(){
        this.lastupdate = new Date();
    }

}
