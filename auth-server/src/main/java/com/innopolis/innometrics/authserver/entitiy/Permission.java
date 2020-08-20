package com.innopolis.innometrics.authserver.entitiy;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Permission_id.class)
public class Permission {

    @Id
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "page", referencedColumnName = "page")
    private Page page;

    @Id
    @Column
    private String role;


}
