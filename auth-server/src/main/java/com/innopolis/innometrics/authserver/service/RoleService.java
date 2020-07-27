package com.innopolis.innometrics.authserver.service;

import com.innopolis.innometrics.authserver.DTO.PageListResponse;
import com.innopolis.innometrics.authserver.DTO.PageResponse;
import com.innopolis.innometrics.authserver.entitiy.Page;
import com.innopolis.innometrics.authserver.entitiy.Permission;
import com.innopolis.innometrics.authserver.entitiy.Role;
import com.innopolis.innometrics.authserver.repository.PageRepository;
import com.innopolis.innometrics.authserver.repository.PermissionRepository;
import com.innopolis.innometrics.authserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PageRepository pageRepository;


    public PageListResponse getPagesWithIconsForRole(String role){
        Set<Permission> permissions = roleRepository.findByName(role).getPermissions();
        PageListResponse pages = new PageListResponse();

        for (Permission permission : permissions) {
            PageResponse temp = new PageResponse(permission.getPage().getPage(), permission.getPage().getIcon());
            pages.getPageList().add(temp);
        }
        return pages;
    }

}
