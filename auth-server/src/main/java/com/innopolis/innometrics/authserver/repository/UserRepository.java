package com.innopolis.innometrics.authserver.repository;

import com.innopolis.innometrics.authserver.entitiy.Project;
import com.innopolis.innometrics.authserver.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String > {
    User findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query(
            value = "select u.* from innometricsAuth.user u, innometricsAuth.project_users pu\n" +
                    "where isactive = 'Y'\n" +
                    "and u.email = pu.email\n" +
                    "and CAST(pu.projectID AS TEXT) = COALESCE(cast(:ProjectID as text), cast(pu.projectid as TEXT))",
            nativeQuery = true
    )
    List<User> findAllActive(@Param("ProjectID") String projectID);

    List<User> findAll();
}