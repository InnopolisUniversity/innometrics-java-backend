package com.innopolis.innometrics.authserver.repository;

import com.innopolis.innometrics.authserver.entitiy.TemporalToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporalTokenRepository extends JpaRepository<TemporalToken, String > {
    TemporalToken findByEmail (String email);
    TemporalToken findByEmailAndTemporalToken(String email, String temporal_token);
}
