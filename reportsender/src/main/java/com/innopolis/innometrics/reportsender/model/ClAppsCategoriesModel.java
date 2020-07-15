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
@Entity(name = "cl_apps_categories")
@Table(name = "cl_apps_categories", schema = "innometricsconfig")
public class ClAppsCategoriesModel {

    @Id
    Integer appid;

//    Integer catid;

    String appname;

    String executablefile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catid")
    ClCategoriesModel category;




}
