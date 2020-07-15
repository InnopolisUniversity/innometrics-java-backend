package com.innopolis.innometrics.reportsender.repository;

import com.innopolis.innometrics.reportsender.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, String> {
}
