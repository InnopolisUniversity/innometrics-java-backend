package com.innopolis.innometrics.authserver.service;

import com.innopolis.innometrics.authserver.DTO.*;
import com.innopolis.innometrics.authserver.entitiy.Page;
import com.innopolis.innometrics.authserver.entitiy.Permission;
import com.innopolis.innometrics.authserver.entitiy.Role;
import com.innopolis.innometrics.authserver.repository.PageRepository;
import com.innopolis.innometrics.authserver.repository.PermissionRepository;
import com.innopolis.innometrics.authserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    PermissionRepository permissionRepository;



    public PageListResponse getPagesWithIconsForRole(String role){
        Set<Permission> permissions = roleRepository.findByName(role).getPermissions();
        PageListResponse pages = new PageListResponse();

        for (Permission permission : permissions) {
            PageResponse temp = new PageResponse(permission.getPage().getPage(), permission.getPage().getIcon());
            pages.addPageResponse(temp);
        }
        return pages;
    }

    public RoleResponse getRole(String roleName){
        Role role = roleRepository.findByName(roleName);
        if(role!=null){
            return RoleResponseFromRole(role);
        }
        return null;
    }

    public RoleListResponse getRoles(){
        List<Role> roles = roleRepository.findAll();

        if(!roles.isEmpty()){
            RoleListResponse roleResponseList = new RoleListResponse();
            for (Role role : roles) {
                roleResponseList.addRoleResponse(RoleResponseFromRole(role));
            }
            return roleResponseList;
        }
        return null;
    }


    public RoleResponse createRole(RoleRequest roleRequest){
        Role role = new Role();

        role.setName(roleRequest.getName());
        role.setCreatedby(roleRequest.getCreatedby());
        role.setCreationdate(roleRequest.getCreationdate());
        role.setDescription(roleRequest.getDescription());
        role.setIsactive(roleRequest.getIsactive());
        role.setLastupdate(roleRequest.getLastupdate());
        role.setUpdateby(roleRequest.getUpdateby());

        roleRepository.save(role);

        List<PageRequest> pages = roleRequest.getPages();

        Set<Permission> permissions = new HashSet<Permission>();
        for (PageRequest pageRequest : pages) {
            Page page = pageRepository.findByPage(pageRequest.getPage());
            Permission permission = permissionRepository.findByPageAndRole(page, roleRequest.getName());
            if (page != null){
                if(permission == null){
                    permission = new Permission(page, roleRequest.getName());
                    permission = permissionRepository.save(permission);
                }
            } else {
                page = new Page(pageRequest.getPage(),pageRequest.getIcon());
                page = pageRepository.save(page);
                permission = new Permission(page, roleRequest.getName());
                permission = permissionRepository.save(permission);

            }

            permissions.add(permission);
        }

        role.setPermissions(permissions);

        role = roleRepository.save(role);

        return RoleResponseFromRole(role);
    }

    @Transactional
    public RoleResponse updateRole(RoleRequest roleRequest){
        Role role = roleRepository.findByName(roleRequest.getName());

        role.setName(roleRequest.getName());
        role.setCreatedby(roleRequest.getCreatedby());
        role.setCreationdate(roleRequest.getCreationdate());
        role.setDescription(roleRequest.getDescription());
        role.setIsactive(roleRequest.getIsactive());
        role.setLastupdate(roleRequest.getLastupdate());
        role.setUpdateby(roleRequest.getUpdateby());



        // delete previous
//        Set<Permission> permissionSet = roleRepository.findByName(roleRequest.getName()).getPermissions();
//        for (Permission permission : permissionSet) {
//            Page page = permission.getPage();
//            permissionRepository.findAllByPage();
//            permissionRepository.delete(permission);
//        }

//        List<PageRequest> pages = roleRequest.getPages();
//        Set<Permission> permissions = new HashSet<Permission>();
//        for (PageRequest pageRequest : pages) {
//            Page page = pageRepository.findByPage(pageRequest.getPage());
//            Permission permission;
//            if (page != null) {
//                permission = permissionRepository.findByPageAndRole(page,roleRequest.getName());
//                if(permission==null){
//                    permission = new Permission(page, roleRequest.getName());
//                    permissionRepository.save(permission);
//                }
//
//            } else {
//                page = new Page(pageRequest.getPage(), pageRequest.getIcon());
//                permission = new Permission(page, roleRequest.getName());
//                permissionRepository.save(permission);
//                pageRepository.save(page);
//            }
//
//            permissions.add(permission);
//        }

        //        //delete previous
//        List<Permission> allPermissionsForRoleWithLast = permissionRepository.findAllByRole(roleRequest.getName());
//        if(allPermissionsForRoleWithLast.size() > pages.size()){
//            for (Permission permission : allPermissionsForRoleWithLast) {
//                if(pages.contains())
//            }
//        }

        permissionRepository.deleteAllByRole(roleRequest.getName());


        List<PageRequest> pages = roleRequest.getPages();
        Set<Permission> permissions = new HashSet<Permission>();
        for (PageRequest pageRequest : pages) {
            Page page = pageRepository.findByPage(pageRequest.getPage());
            Permission permission;
            if (page != null) {
                permission = new Permission(page, roleRequest.getName());
                permissionRepository.save(permission);
            } else {
                page = new Page(pageRequest.getPage(), pageRequest.getIcon());
                permission = new Permission(page, roleRequest.getName());
                permissionRepository.save(permission);
                pageRepository.save(page);
            }

            permissions.add(permission);
        }

        role.setPermissions(permissions);

        role = roleRepository.save(role);

        return RoleResponseFromRole(role);
    }


    private RoleResponse RoleResponseFromRole(Role role){
        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setName(role.getName());
        roleResponse.setCreatedby(role.getCreatedby());
        roleResponse.setCreationdate(role.getCreationdate());
        roleResponse.setDescription(role.getDescription());
        roleResponse.setIsactive(role.getIsactive());
        roleResponse.setLastupdate(role.getLastupdate());
        roleResponse.setUpdateby(role.getUpdateby());

        PageListResponse pages = new PageListResponse();

        for (Permission permission : role.getPermissions()) {
            PageResponse temp = new PageResponse(permission.getPage().getPage(), permission.getPage().getIcon());
            pages.addPageResponse(temp);
        }

        roleResponse.setPages(pages);

        return roleResponse;
    }

    private Role RoleFromRoleRequest(RoleRequest roleRequest){
        Role role = new Role();

        role.setName(roleRequest.getName());
        role.setCreatedby(roleRequest.getCreatedby());
        role.setCreationdate(roleRequest.getCreationdate());
        role.setDescription(roleRequest.getDescription());
        role.setIsactive(roleRequest.getIsactive());
        role.setLastupdate(roleRequest.getLastupdate());
        role.setUpdateby(roleRequest.getUpdateby());

        roleRepository.save(role);

        List<PageRequest> pages = roleRequest.getPages();

        Set<Permission> permissions = new HashSet<Permission>();
        for (PageRequest pageRequest : pages) {
            Page page = pageRepository.findByPage(pageRequest.getPage());
            Permission permission;
            if (page != null){
                permission = permissionRepository.findByPageAndRole(page, roleRequest.getName());
                if(permission!=null){
                    permissions.add(permission);
                } else {
                    permission = new Permission(page, roleRequest.getName());
                    permissionRepository.save(permission);
                }
            } else {
                page = new Page(pageRequest.getPage(),pageRequest.getIcon());
                permission = new Permission(page, roleRequest.getName());
                permissionRepository.save(permission);
                pageRepository.save(page);
            }

            permissions.add(permission);
        }

        role.setPermissions(permissions);
        return role;
    }

    public Role findByName(String roleName){
        return roleRepository.findByName(roleName);
    }

    public PermissionResponse createPermissions(PermissionResponse permissionResponse){
        PermissionResponse permissionResponseOut  = new PermissionResponse();

        if(roleRepository.findByName(permissionResponse.getRole()) != null){
            permissionResponseOut.setRole(permissionResponse.getRole());
            List<Page> pages = permissionResponse.getPages();
            for (Page page : pages) {
                if(pageRepository.findByPage(page.getPage()) != null){

                    if(permissionRepository.findByPageAndRole(page,permissionResponse.getRole()) == null){
                        Permission permission = new Permission(page, permissionResponse.getRole());
                        permission = permissionRepository.save(permission);
                        Role role = roleRepository.findByName(permissionResponse.getRole());
                        if(!role.getPermissions().contains(permission)){
                            role.getPermissions().add(permission);
                            roleRepository.save(role);
                        }
                    }
                } else {
                    page = pageRepository.save(page);
                    Permission permission = new Permission(page, permissionResponse.getRole());
                    permissionRepository.save(permission);
                    Role role = roleRepository.findByName(permissionResponse.getRole());
                    role.getPermissions().add(permission);
                    roleRepository.save(role);
                }
                permissionResponseOut.addPage(page);
            }
            return permissionResponseOut;
        }

       return null;
    }
}
