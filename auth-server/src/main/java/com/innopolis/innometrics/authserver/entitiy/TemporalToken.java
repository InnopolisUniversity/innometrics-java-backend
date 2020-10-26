package com.innopolis.innometrics.authserver.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TemporalToken {

    @Id
    @Column(updatable = false)
    private String email;

    @Column(updatable = false)
    private String temporalToken;

    @Column(name = "expiration_date", updatable = false)
    private Timestamp expirationDate;

}
