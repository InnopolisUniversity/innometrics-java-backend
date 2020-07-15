package com.innopolis.innometrics.reportsender.model;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "cl_categories")
@Table(name = "cl_categories", schema = "innometricsconfig")
public class ClCategoriesModel {

    @Id
    Integer catid;

    String catname;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    List<ClAppsCategoriesModel> apps;
}
