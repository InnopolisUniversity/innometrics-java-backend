package com.innopolis.innometrics.agentsgateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agentconfigdetails")
public class Agentconfigdetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configdetid", updatable = false)
    private Integer configDetId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "methodid")
    private Agentconfigmethods agentconfigmethods;

    @Column
    private String paramname;

    @Column
    private String paramtype;

    @Column
    private String requestparam;

    @Column
    private String requesttype;

    @Column
    private String isactive;

    @Column
    private String defaultvalue;

    @CreationTimestamp
    @Column(name = "creationdate", insertable = false, updatable = false)
    private Date creationdate;

    @CreatedBy
    @Column(name = "createdby", insertable = false, updatable = false)
    private String createdby;

    @UpdateTimestamp
    @Column(name = "lastupdate", insertable = false)
    private Date lastupdate;

    @LastModifiedBy
    @Column(name = "updateby", insertable = false)
    private String updateby;

}
