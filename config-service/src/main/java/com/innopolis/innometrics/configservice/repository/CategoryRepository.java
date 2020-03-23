package com.innopolis.innometrics.configservice.repository;

import com.innopolis.innometrics.configservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findBycatname(String cat_name);
    Boolean existsBycatname(String cat_name);
}
