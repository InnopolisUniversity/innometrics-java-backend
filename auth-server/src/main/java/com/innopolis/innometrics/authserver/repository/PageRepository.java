package com.innopolis.innometrics.authserver.repository;

import com.innopolis.innometrics.authserver.entitiy.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, String > {
    Page findByPage(String page);
}
