package com.innopolis.innometrics.restapi.repository;

import com.innopolis.innometrics.restapi.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer > {

    List<Activity> findByEmail(String email);
    /*
    @RequestParam Integer offset,
                                                      @RequestParam Integer amount_to_return,
                                                      @RequestParam String filters,
                                                      @RequestParam Date start_time,
                                                      @RequestParam Date end_time,
                                                      @RequestHeader String Token
    * */

    //@Query(value="SELECT e FROM Employee e WHERE e.name LIKE ?1 ORDER BY e.id offset ?2 limit ?3", nativeQuery = true)
    //public List<Employee> findByNameAndMore(String name, int offset, int limit);


    //@Query("SELECT a FROM activity p WHERE p.end_time = LOWER(:start_time)")
    //public List<Activity> find(@Param("start_time") String lastName, @Param("offset") int offset, @Param("limit") int limit);
}