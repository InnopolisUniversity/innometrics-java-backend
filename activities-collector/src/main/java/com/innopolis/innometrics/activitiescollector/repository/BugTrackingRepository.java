package com.innopolis.innometrics.activitiescollector.repository;

import com.innopolis.innometrics.activitiescollector.entity.BugTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BugTrackingRepository extends JpaRepository<BugTracking, Integer > {
    @Query(value = "select * from innometrics.bug_tracking p where p.status = ?1", nativeQuery = true)
    List<BugTracking> findAllByStatus(Boolean status);

    @Query(value = "select * from innometrics.bug_tracking p " +
            "where p.status = ?1 and p.creationdate between ?2 AND ?3",
            nativeQuery = true)
    List<BugTracking> findAllByStatusBetweenDates(boolean status, Timestamp d1, Timestamp d2);

    @Query(value = "select * from innometrics.bug_tracking p " +
        "where p.creationdate between ?1 AND ?2",
        nativeQuery = true)
    List<BugTracking> findAllBetweenDates(Timestamp d1, Timestamp d2);

}
