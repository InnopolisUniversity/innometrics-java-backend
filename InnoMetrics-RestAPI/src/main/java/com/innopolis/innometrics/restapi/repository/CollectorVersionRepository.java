package com.innopolis.innometrics.restapi.repository;

import com.innopolis.innometrics.restapi.entity.CollectorVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectorVersionRepository extends JpaRepository<CollectorVersion, Integer> {
    CollectorVersion findByOsversion(String osversion);
}
