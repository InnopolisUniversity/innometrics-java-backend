package com.innopolis.innometrics.authserver.repository;

import com.innopolis.innometrics.authserver.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String > {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}