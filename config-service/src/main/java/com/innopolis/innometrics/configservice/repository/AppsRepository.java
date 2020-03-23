package com.innopolis.innometrics.configservice.repository;

import com.innopolis.innometrics.configservice.entities.App_category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppsRepository extends JpaRepository<App_category, Integer> {
}
