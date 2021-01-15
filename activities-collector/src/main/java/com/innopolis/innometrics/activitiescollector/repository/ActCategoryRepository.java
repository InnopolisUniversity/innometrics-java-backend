package com.innopolis.innometrics.activitiescollector.repository;

import com.innopolis.innometrics.activitiescollector.DTO.ICategoriesReport;
import com.innopolis.innometrics.activitiescollector.DTO.ITimeReportByUser;
import com.innopolis.innometrics.activitiescollector.entity.ActAppxCategory;
import com.innopolis.innometrics.activitiescollector.entity.ActCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ActCategoryRepository extends JpaRepository<ActCategories, Integer > {
    List<ActCategories> findByCatname(String categoryName);
    Boolean existsByCatname(String categoryName);


    @Query( value =
            "select cat.catname catname, cat.catdescription catdescription, \n" +
            "  case when sum(dailysum) >= interval '0' then\n" +
            "    CONCAT(trunc(EXTRACT(EPOCH FROM ( sum(dailysum) ))/60/60),':', TO_CHAR(TO_TIMESTAMP(EXTRACT(EPOCH FROM ( sum(dailysum) ))), 'MI:SS')) \n" +
            "  else \n" +
            "   '00:00:00'\n" +
            "  end\n" +
            "    timeused\n" +
            "  from(\n" +
            "    select ac.catid, executable_name, date_trunc('day', captureddate) captureddate, max(dailysum) dailysum\n" +
            "      from innometrics.cumlativerepactivity a\n" +
            "      left join innometricsconfig.cl_apps_categories ac\n" +
            "        on a.executable_name = ac.executablefile\n" +
            "     where  (\n" +
            "             (email = COALESCE(cast(:email as text), email) and cast(:ProjectID as text) is null)\n" +
            "             or\n" +
            "             ( \n" +
            "              email in (\n" +
            "                select email\n" +
            "                  from innometricsauth.project_users\n" +
            "                  where cast(projectid as text) = cast(:ProjectID as text) )\n" +
            "             )\n" +
            "            )\n" +
            "       and captureddate >= date_trunc('day', TO_TIMESTAMP(COALESCE(cast(:min_date as text), '1/1/1900'), 'DD/MM/YYYY'))\n" +
            "       and captureddate < date_trunc('day', TO_TIMESTAMP(COALESCE(cast(:max_date as text), '31/12/2999'), 'DD/MM/YYYY'))\n" +
            "     group by executable_name, date_trunc('day', captureddate), ac.catid) catapp right join innometricsconfig.cl_categories cat\n" +
            "  on catapp.catid = cat.catid\n" +
            "  group by cat.catname, cat.catdescription;", nativeQuery = true)
    List<ICategoriesReport> getTimeReport(@Param("ProjectID") String projectID, @Param("email") String email, @Param("min_date") String min_date, @Param("max_date") String max_date);

}