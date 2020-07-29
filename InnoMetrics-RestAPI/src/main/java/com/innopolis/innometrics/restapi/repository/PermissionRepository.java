package com.innopolis.innometrics.restapi.repository;

import com.innopolis.innometrics.restapi.entitiy.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {


}
