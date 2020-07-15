package com.innopolis.innometrics.reportsender.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "user")
@Table(name = "user", schema = "innometrics")
public class UserModel {

    @Id
    String email;

}
