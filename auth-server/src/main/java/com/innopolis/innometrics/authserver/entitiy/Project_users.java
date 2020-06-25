package com.innopolis.innometrics.authserver.entitiy;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class Project_users  implements Serializable {
    @Column
    private Integer projectid;

    @Column
    private String email;
}
