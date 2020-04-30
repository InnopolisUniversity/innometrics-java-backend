package com.innopolis.innometrics.activitiescollector.repository;

import com.innopolis.innometrics.activitiescollector.DTO.ActivitiesReportByUser;
import com.innopolis.innometrics.activitiescollector.DTO.IActivitiesReportByUser;
import com.innopolis.innometrics.activitiescollector.DTO.ITimeReportByUser;
import com.innopolis.innometrics.activitiescollector.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer > {

    List<Activity> findByEmail(String email);

    @Modifying
    @Query( value =
            "select a.email, \n" +
            "       executable_name, \n" +
            "   CONCAT(trunc(EXTRACT(EPOCH FROM ( max(used_time) ))/60/60),':',\n" +
            "       TO_CHAR(TO_TIMESTAMP(EXTRACT(EPOCH FROM ( max(used_time) ))), 'MI:SS')) time_used,\n" +
            "   cast(to_char(date_trunc('day', captureddate), 'DD/MM/YYYY') as varchar) activity_day\n" +
            "  from innometrics.cumlativerepactivity a, innometricsauth.project_users pu\n" +
            " where pu.email = COALESCE(cast(:email as text), a.email)\n" +
            "   and CAST(pu.projectID AS TEXT) = COALESCE(cast(:ProjectID as text), cast(pu.projectid as TEXT))\n" +
            "   and captureddate >= date(COALESCE(cast(:min_date as text), '1/1/1900')) \n" +
            "   and captureddate < date(COALESCE(cast(:max_date as text), '12/31/2999'))\n" +
            "   and pu.email = a.email\n" +
            " group by a.email, executable_name, date_trunc('day', captureddate)\n" +
            " order by activity_day asc, 1 asc;", nativeQuery = true)
    List<IActivitiesReportByUser> getActivitiesReport(@Param("ProjectID") String projectID, @Param("email") String email, @Param("min_date") Date min_date, @Param("max_date") Date max_date);


    @Query( value =
            "select a.email, \n" +
            "       CONCAT(trunc(EXTRACT(EPOCH FROM ( max(used_time) ))/60/60),':',\n" +
            "       TO_CHAR(TO_TIMESTAMP(EXTRACT(EPOCH FROM ( max(used_time) ))), 'MI:SS')) time_used,\n" +
            "       cast(to_char(date_trunc('day', captureddate), 'DD/MM/YYYY') as varchar) activity_day\n" +
            "  from innometrics.cumlativerepactivity a, innometricsauth.project_users pu\n" +
            " where pu.email = COALESCE(cast(:email as text), a.email)\n" +
            "   and CAST(pu.projectID AS TEXT) = COALESCE(cast(:ProjectID as text), cast(pu.projectid as TEXT))\n" +
            "   and captureddate >= date(COALESCE(cast(:min_date as text), '1/1/1900')) \n" +
            "   and captureddate < date(COALESCE(cast(:max_date as text), '12/31/2999'))\n" +
            "   and pu.email = a.email\n" +
            " group by a.email, date_trunc('day', captureddate)\n" +
            " order by activity_day asc, 1 asc;", nativeQuery = true)
    List<ITimeReportByUser> getTimeReport(@Param("ProjectID") String projectID, @Param("email") String email, @Param("min_date") Date min_date, @Param("max_date") Date max_date);

}