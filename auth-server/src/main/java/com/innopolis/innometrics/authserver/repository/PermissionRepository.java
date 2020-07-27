package com.innopolis.innometrics.authserver.repository;

import com.innopolis.innometrics.authserver.entitiy.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String > {


}
