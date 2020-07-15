package com.innopolis.innometrics.authserver.repository;

import com.innopolis.innometrics.authserver.entitiy.Teammembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeammembersRepository extends JpaRepository<Teammembers, Integer> {
    List<Teammembers> findByTeamid(Integer teamid);
    Boolean existsByTeamidAndEmail(Integer teamid, String email);
}

