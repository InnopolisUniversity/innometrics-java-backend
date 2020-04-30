package com.innopolis.innometrics.activitiescollector.repository;

import com.innopolis.innometrics.activitiescollector.entity.ActAppxCategory;
import com.innopolis.innometrics.activitiescollector.entity.ActCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActCategoryRepository extends JpaRepository<ActCategories, Integer > {
    List<ActCategories> findByCategoryname(String categoryName);
    Boolean existsByCategoryname(String categoryName);
}