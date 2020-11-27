package com.innopolis.innometrics.restapi.repository;

import com.innopolis.innometrics.restapi.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, String> {
    Page findByPage(String page);
}
