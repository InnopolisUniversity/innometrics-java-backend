package com.innopolis.innometrics.restapi.repository;

import com.innopolis.innometrics.restapi.entity.MeasurementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementTypeRepository  extends JpaRepository<MeasurementType, Integer > {
    Boolean existsByLabel(String Label);
    MeasurementType findByLabel(String Label);
    MeasurementType findByMeasurementtypeid(Integer measurementtypeid);
}
