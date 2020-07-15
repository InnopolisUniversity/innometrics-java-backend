package com.innopolis.innometrics.reportsender.model;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "cumlativerepactivity")
@Table(name = "cumlativerepactivity", schema = "innometrics")
@TypeDef(
        typeClass = PostgreSQLIntervalType.class,
        defaultForType = Duration.class
)
public class CumlativeRepActivityModel {

    @Id
    Integer reportid;

//    @Column(name = "email")
    String email;

//    @Column(name = "executable_name")
    String executableName;

    @Column(name = "used_time",
            columnDefinition = "interval")
    Duration usedTime;

    Timestamp creationdate;
}
