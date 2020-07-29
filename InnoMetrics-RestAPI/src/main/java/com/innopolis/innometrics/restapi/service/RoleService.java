package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.*;
import com.innopolis.innometrics.restapi.entitiy.Page;
import com.innopolis.innometrics.restapi.entitiy.Permission;
import com.innopolis.innometrics.restapi.entitiy.Role;
import com.innopolis.innometrics.restapi.repository.PageRepository;
import com.innopolis.innometrics.restapi.repository.RoleRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class RoleService {
    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;
    private String baseURL = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/Role/";
    //private String baseURL = "http://localhost:9092/AdminAPI/Role/";

    @HystrixCommand(commandKey = "getPagesWithIconsForRole",
            fallbackMethod = "findPagesForRoleFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public List<Page> getPagesWithIconsForRole(String role){
        PageListResponse pageListResponse;
        String uri = baseURL + "Permissions/"  + role;


        HttpEntity<PageListResponse> entity = new HttpEntity<>(null);
        ResponseEntity<PageListResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, PageListResponse.class);

        HttpStatus status = response.getStatusCode();
        pageListResponse=response.getBody();

        if (status == HttpStatus.NO_CONTENT) return null;
        List<Page> pages = new ArrayList<>();

        for (PageResponse pageResponse: pageListResponse.getPageList()){
            Page page = new Page();
            page.setIcon(pageResponse.getIcon());
            page.setPage(pageResponse.getPage());

            pages.add(page);

        }

        return pages;
    }

    public List<Page> findPagesForRoleFallback(String role, Throwable exception) {
        LOG.warn("findPagesForRoleFallback method used");
        LOG.warn(exception);
        return null;
    }

    @HystrixCommand(commandKey = "getRole", fallbackMethod = "getRoleFallback",
     commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public RoleResponse getRole(String roleName){


        String uri = baseURL   + roleName;

        HttpEntity<PageListResponse> entity = new HttpEntity<>(null);
        ResponseEntity<RoleResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, RoleResponse.class);

        HttpStatus status = response.getStatusCode();
        RoleResponse roleResponse=response.getBody();

        if (status == HttpStatus.NO_CONTENT) return null;

        return roleResponse;
        //return RoleFromRoleResponse(roleResponse);
    }

    public RoleResponse getRoleFallback(String roleName, Throwable exception) {
        LOG.warn("getRoleFallback method used");
        LOG.warn(exception);
        return null;
    }


    @HystrixCommand(commandKey = "getRoles", fallbackMethod = "getRolesFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public RoleListResponse getRoles(){


        String uri = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/Roles";

        HttpEntity<RoleListResponse> entity = new HttpEntity<>(null);
        ResponseEntity<RoleListResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, RoleListResponse.class);

        HttpStatus status = response.getStatusCode();
        RoleListResponse roleResponse=response.getBody();

        if (status == HttpStatus.NO_CONTENT) return null;

        return roleResponse;
    }

    public RoleListResponse getRolesFallback(Throwable exception) {
        LOG.warn("findRoleFallback method used");
        LOG.warn(exception);
        return null;
    }



    @HystrixCommand(commandKey = "createRole", fallbackMethod = "createRoleFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public RoleResponse createRole(RoleRequest roleRequest){
        String uri = baseURL;

        HttpEntity<RoleRequest> entity = new HttpEntity<>(roleRequest);
        ResponseEntity<RoleResponse> response = restTemplate.exchange(uri, HttpMethod.POST, entity, RoleResponse.class);

        HttpStatus status = response.getStatusCode();
        RoleResponse restCall = response.getBody();


        if (status == HttpStatus.CREATED) {

            return restCall;
        }
        return null;
    }

    public RoleResponse createRoleFallback(RoleRequest roleRequest, Throwable exception) {
        LOG.warn("createRoleFallback method used");
        LOG.warn(exception);
        return null;
    }


    @HystrixCommand( commandKey = "updateRole", fallbackMethod = "updateRoleFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public RoleResponse updateRole(RoleRequest roleRequest) {

        String uri = baseURL;

        HttpEntity<RoleRequest> entity = new HttpEntity<>(roleRequest);
        ResponseEntity<RoleResponse> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, RoleResponse.class);

        HttpStatus status = response.getStatusCode();
        RoleResponse restCall = response.getBody();

        if (status == HttpStatus.CREATED) {
            return restCall;
        }

        return null;

    }


    public RoleResponse updateRoleFallback(RoleRequest roleRequest, Throwable exception) {
        LOG.warn("updateRoleFallback method used");
        LOG.warn(exception);
        return null;
    }


    @HystrixCommand(commandKey = "updateRole", fallbackMethod = "updateRoleFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")})
    public RoleResponse updateRolePermissions(RoleRequest roleRequest){
        String uri = baseURL;

        HttpEntity<RoleRequest> entity = new HttpEntity<>(roleRequest);
        ResponseEntity<RoleResponse> response = restTemplate.exchange(uri, HttpMethod.POST, entity, RoleResponse.class);

        HttpStatus status = response.getStatusCode();
        RoleResponse restCall = response.getBody();


        if (status == HttpStatus.CREATED) {

            return restCall;
        }
        return null;
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

        List<PageRequest> pages = roleRequest.getPages();
        Set<Permission> permissions = new HashSet<Permission>();
        for (PageRequest pageRequest : pages) {
            Permission permission = new Permission();
            permission.setRole(roleRequest.getName());
            permission.setPage(new Page(pageRequest.getPage(),pageRequest.getIcon()));
            permissions.add(permission);
        }

        role.setPermissions(permissions);

        return role;
    }

    public Role RoleFromRoleResponse(RoleResponse roleResponse){
        Role role = new Role();

        role.setName(roleResponse.getName());
        role.setCreatedby(roleResponse.getCreatedby());
        role.setCreationdate(roleResponse.getCreationdate());
        role.setDescription(roleResponse.getDescription());
        role.setIsactive(roleResponse.getIsactive());
        role.setLastupdate(roleResponse.getLastupdate());
        role.setUpdateby(roleResponse.getUpdateby());

        PageListResponse pageListResponse = roleResponse.getPages();
        Set<Permission> permissions = new HashSet<Permission>();
        for (PageResponse pageResponse : pageListResponse.getPageList()) {
            Permission permission = new Permission();
            permission.setRole(roleResponse.getName());
            permission.setPage(new Page(pageResponse.getPage(),pageResponse.getIcon()));
            permissions.add(permission);
        }

        role.setPermissions(permissions);



        return role;
    }

}
