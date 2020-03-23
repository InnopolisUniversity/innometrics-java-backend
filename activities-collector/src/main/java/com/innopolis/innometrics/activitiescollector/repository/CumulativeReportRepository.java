package com.innopolis.innometrics.activitiescollector.repository;

import com.innopolis.innometrics.activitiescollector.entity.CumulativeReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CumulativeReportRepository extends JpaRepository<CumulativeReport, Integer > {
    List<CumulativeReport> getAllByEmail(String email);
}
