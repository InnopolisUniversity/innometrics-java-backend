package com.innopolis.innometrics.activitiescollector.repository;

import com.innopolis.innometrics.activitiescollector.DTO.ActivitiesReportByUser;
import com.innopolis.innometrics.activitiescollector.DTO.IActivitiesReportByUser;
import com.innopolis.innometrics.activitiescollector.DTO.IActivitiesReportByUserAndDay;
import com.innopolis.innometrics.activitiescollector.DTO.ITimeReportByUser;
import com.innopolis.innometrics.activitiescollector.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer>, PagingAndSortingRepository<Activity, Integer> {

    @Modifying
    @Query( value =
            "select activityid, activitytype, idle_activity, email, start_time, end_time, executable_name, browser_url, browser_title, ip_address, mac_address, value, creationdate, createdby, lastupdate, updateby, projectid, categoryname, pid, osversion\n" +
            "from innometrics.activity\n" +
            "where email = :email\n" +
            "and start_time between date_trunc('day', TO_TIMESTAMP(:ReportDate, 'DD/MM/YYYY')) and date_trunc('day', TO_TIMESTAMP(:ReportDate, 'DD/MM/YYYY')) + interval '1 day' - interval '1 second';", nativeQuery = true)
    List<IActivitiesReportByUserAndDay> getActivitiesPerDay(@Param("email") String email, @Param("ReportDate") String ReportDate);

    @Modifying
    @Query( value =
            "select a.email, \n" +
            "       executable_name, \n" +
            "   CONCAT(trunc(EXTRACT(EPOCH FROM ( sum(used_time) ))/60/60),':',\n" +
            "       TO_CHAR(TO_TIMESTAMP(EXTRACT(EPOCH FROM ( sum(used_time) ))), 'MI:SS')) time_used,\n" +
            "   cast(to_char(date_trunc('day', captureddate), 'DD/MM/YYYY') as varchar) activity_day\n" +
            "  from innometrics.cumlativerepactivity a, innometricsauth.project_users pu\n" +
            " where pu.email = COALESCE(cast(:email as text), a.email)\n" +
            "   and pu.role != 'MANAGER' \n" +
            "   and CAST(pu.projectID AS TEXT) = COALESCE(cast(:ProjectID as text), cast(pu.projectid as TEXT))\n" +
            "   and captureddate >= date_trunc('day', TO_TIMESTAMP(COALESCE(cast(:min_date as text), '1/1/1900'), 'DD/MM/YYYY')) \n" +
            "   and captureddate < date_trunc('day', TO_TIMESTAMP(COALESCE(cast(:max_date as text), '31/12/2999'), 'DD/MM/YYYY'))\n" +
            "   and pu.email = a.email\n" +
            " group by a.email, executable_name, date_trunc('day', captureddate)\n" +
            " order by date_trunc('day', captureddate) asc, 1 asc;", nativeQuery = true)
    List<IActivitiesReportByUser> getActivitiesReport(@Param("ProjectID") String projectID, @Param("email") String email, @Param("min_date") String min_date, @Param("max_date") String max_date);


    @Query( value =
            "select a.email, \n" +
            "       CONCAT(trunc(EXTRACT(EPOCH FROM ( sum(used_time) ))/60/60),':',\n" +
            "       TO_CHAR(TO_TIMESTAMP(EXTRACT(EPOCH FROM ( sum(used_time) ))), 'MI:SS')) time_used,\n" +
            "       cast(to_char(date_trunc('day', captureddate), 'DD/MM/YYYY') as varchar) activity_day\n" +
            "  from innometrics.cumlativerepactivity a, innometricsauth.project_users pu\n" +
            " where pu.email = COALESCE(cast(:email as text), a.email)\n" +
            "   and pu.role != 'MANAGER' \n" +
            "   and CAST(pu.projectID AS TEXT) = COALESCE(cast(:ProjectID as text), cast(pu.projectid as TEXT))\n" +
            "   and captureddate >= date_trunc('day', TO_TIMESTAMP(COALESCE(cast(:min_date as text), '1/1/1900'), 'DD/MM/YYYY')) \n" +
            "   and captureddate < date_trunc('day', TO_TIMESTAMP(COALESCE(cast(:max_date as text), '31/12/2999'), 'DD/MM/YYYY'))\n" +
            "   and pu.email = a.email\n" +
            " group by a.email, date_trunc('day', captureddate)\n" +
            " order by date_trunc('day', captureddate) asc, 1 asc;", nativeQuery = true)
    List<ITimeReportByUser> getTimeReport(@Param("ProjectID") String projectID, @Param("email") String email, @Param("min_date") String min_date, @Param("max_date") String max_date);


    @Modifying
    @Query(value = "Delete from innometrics.activity a where a.activityid in :idList", nativeQuery = true)
    Integer deleteActivitiesWithIds(List<Integer> idList);
}