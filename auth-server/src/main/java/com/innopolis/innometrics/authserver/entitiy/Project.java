package com.innopolis.innometrics.authserver.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer projectID;

    @Column
    private String name;

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



    //@JsonIgnore
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")//, cascade = CascadeType.ALL)
    //private Set<User> managers = new HashSet<>();

    //@JsonIgnore
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")//, cascade = CascadeType.ALL)
    //private Set<User> users = new HashSet<>();

    //@JsonIgnore
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")//, cascade = CascadeType.ALL)
    //private Set<User> invited_managers = new HashSet<>();

    //@JsonIgnore
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")//, cascade = CascadeType.ALL)
    //private Set<User> invited_users = new HashSet<>();

    /*
    public Project() {
    }

    public Project(Integer projectID, String name, String isactive, Date creationdate, String createdby, Date lastupdate, String updateby, Set<User> managers, Set<User> users, Set<User> invited_managers, Set<User> invited_users) {
        this.projectID = projectID;
        this.name = name;
        this.isactive = isactive;
        this.creationdate = creationdate;
        this.createdby = createdby;
        this.lastupdate = lastupdate;
        this.updateby = updateby;
        /*
        this.managers = managers;
        this.users = users;
        this.invited_managers = invited_managers;
        this.invited_users = invited_users;
        * /
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public Set<User> getManagers() {
        return managers;
    }

    public void setManagers(Set<User> managers) {
        this.managers = managers;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getInvited_managers() {
        return invited_managers;
    }

    public void setInvited_managers(Set<User> invited_managers) {
        this.invited_managers = invited_managers;
    }

    public Set<User> getInvited_users() {
        return invited_users;
    }

    public void setInvited_users(Set<User> invited_users) {
        this.invited_users = invited_users;
    }

     */
}
