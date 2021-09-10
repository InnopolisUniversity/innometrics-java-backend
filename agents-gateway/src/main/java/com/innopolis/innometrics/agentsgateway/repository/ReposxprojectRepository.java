package com.innopolis.innometrics.agentsgateway.repository;

import com.innopolis.innometrics.agentsgateway.entity.Reposxproject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReposxprojectRepository extends JpaRepository<Reposxproject, Integer> {
    List<Reposxproject> findByProjectid(Integer projectid);
    List<Reposxproject> findByRepoid(String repoid);

}
