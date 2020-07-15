package com.innopolis.innometrics.authserver.repository;

import com.innopolis.innometrics.authserver.entitiy.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByCompanyid(Integer companyid);

    Boolean existsByCompanyid(Integer companyid);

    @Query(value = "select * from innometricsauth.company p where p.isactive = 'Y'", nativeQuery = true)
    List<Company> findAllActive();
}

