package com.innopolis.innometrics.reportsender.repository;

import com.innopolis.innometrics.reportsender.model.CumlativeRepActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public interface CumlativeRepActivityRepository extends JpaRepository<CumlativeRepActivityModel, Integer> {
    @Query(value = "from cumlativerepactivity where creationdate between :startDate AND :endDate ")
    public List<CumlativeRepActivityModel> getAllBetweenDates(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query(value = "from cumlativerepactivity where email = :email")
    List<CumlativeRepActivityModel> getAllByEmail(@Param("email") String email);

    List<CumlativeRepActivityModel> findAllByCreationdateBetweenAndEmail(Timestamp startDate, Timestamp endDate, String email);

    List<CumlativeRepActivityModel> findAllByCreationdateBetween(Timestamp startDate, Timestamp endDate);




}
