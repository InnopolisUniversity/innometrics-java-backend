package com.innopolis.innometrics.activitiescollector.repository;

import com.innopolis.innometrics.activitiescollector.entity.ActAppxCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActAppxCategoryRepository extends JpaRepository<ActAppxCategory, Integer > {
    ActAppxCategory findByCatid(Integer categoryId);
}