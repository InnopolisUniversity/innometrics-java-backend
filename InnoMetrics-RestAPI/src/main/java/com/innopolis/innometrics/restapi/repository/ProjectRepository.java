package com.innopolis.innometrics.restapi.repository;

import com.innopolis.innometrics.restapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String > {
    Project findByName(String Name);
    Boolean existsByName(String Name);
}