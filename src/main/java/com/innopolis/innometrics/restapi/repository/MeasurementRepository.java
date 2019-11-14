package com.innopolis.innometrics.restapi.repository;

import com.innopolis.innometrics.restapi.entitiy.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer > {
}
