package com.innopolis.innometrics.restapi.repository;

import com.innopolis.innometrics.restapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String > {
    Role findByName(String RoleName);
}
