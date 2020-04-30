package com.innopolis.innometrics.agentsgateway.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "agentconfig")
public class Agentconfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer agentid;

    @Column
    private String agentname;

    @Column
    private String description;

    @Column
    private String sourcetype;

    @Column
    private String dbschemasource;

    @Column
    private String repoidfield;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "agentid")
    private Set<Agentconfigmethods> methods = new HashSet<Agentconfigmethods>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "agentid")
    private Set<Agentdataconfig> dataconfig = new HashSet<Agentdataconfig>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "agentid")
    private Set<Reposxproject> reposconfig = new HashSet<Reposxproject>();

    public Agentconfig() {
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public String getDbschemasource() {
        return dbschemasource;
    }

    public void setDbschemasource(String dbschemasource) {
        this.dbschemasource = dbschemasource;
    }

    public String getRepoidfield() {
        return repoidfield;
    }

    public void setRepoidfield(String repoidfield) {
        this.repoidfield = repoidfield;
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

    public Set<Agentconfigmethods> getMethods() {
        return methods;
    }

    public void setMethods(Set<Agentconfigmethods> methods) {
        this.methods = methods;
    }

    public Set<Agentdataconfig> getDataconfig() {
        return dataconfig;
    }

    public void setDataconfig(Set<Agentdataconfig> dataconfig) {
        this.dataconfig = dataconfig;
    }

    public Set<Reposxproject> getReposconfig() {
        return reposconfig;
    }

    public void setReposconfig(Set<Reposxproject> reposconfig) {
        this.reposconfig = reposconfig;
    }
}
