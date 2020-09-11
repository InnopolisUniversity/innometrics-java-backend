package com.innopolis.innometrics.authserver.repository;

import com.innopolis.innometrics.authserver.entitiy.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer > {

    List<Profile> findAllByUserEmail(String email);
    Boolean existsByUserEmailAndMacAddress(String email, String macAddress);

    Profile findByUserEmailAndMacAddress(String email, String macAddress);

    Profile findByMacAddress(String macAdress);

}
