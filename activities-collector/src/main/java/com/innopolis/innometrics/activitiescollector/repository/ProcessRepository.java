package com.innopolis.innometrics.activitiescollector.repository;

import com.innopolis.innometrics.activitiescollector.DTO.IProcessReportByUserAndDay;
import com.innopolis.innometrics.activitiescollector.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer > {
    List<Process> findByEmail(String email);

    @Modifying
    @Query( value =
            "select processid, email, executable_name, ip_address, mac_address, value, creationdate, createdby, lastupdate, updateby, projectid, pid, collectedtime, osversion\n" +
            "  from innometrics.process\n" +
            " where email = :email\n" +
            "   and collectedtime between date_trunc('day', TO_TIMESTAMP(:ReportDate, 'DD/MM/YYYY')) and date_trunc('day', TO_TIMESTAMP(:ReportDate, 'DD/MM/YYYY')) + interval '1 day' - interval '1 second';", nativeQuery = true)
    List<IProcessReportByUserAndDay> getProcessesPerDay(@Param("email") String email, @Param("ReportDate") String ReportDate);


    @Modifying
    @Query(value = "Delete from innometrics.process p where p.processid in :idList", nativeQuery = true)
    Void deletePorcessesWithIds(List<Integer> idList);

    @Modifying
    @Query(value =
            "Select distinct executable_name\n" +
            "  from innometrics.process\n" +
            " where lower(email) = :email\n" +
            "   and date_trunc('day', collectedtime) = date_trunc('day', TO_TIMESTAMP(:ReportDate, 'DD/MM/YYYY'));", nativeQuery = true)
    List<String> getCurrentProcessList(@Param("email") String email, @Param("ReportDate") String ReportDate);



    @Modifying
    @Query(value =
            "Select distinct mac_address\n" +
            "  from innometrics.activity\n" +
            " where lower(email) = :email\n" +
            "   and date_trunc('day', start_time) = date_trunc('day', now())\n" +
            " union \n" +
            "Select distinct mac_address\n" +
            "  from innometrics.process\n" +
            " where lower(email) = :email\n" +
            "   and date_trunc('day', collectedtime) = date_trunc('day', now());", nativeQuery = true)
    List<String> getCurrentMACList(@Param("email") String email);

}