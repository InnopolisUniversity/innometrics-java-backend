package com.innopolis.innometrics.authserver.controller;

import com.innopolis.innometrics.authserver.DTO.*;
import com.innopolis.innometrics.authserver.config.JwtToken;
import com.innopolis.innometrics.authserver.entitiy.*;
import com.innopolis.innometrics.authserver.exceptions.ValidationException;
import com.innopolis.innometrics.authserver.service.ProjectService;
import com.innopolis.innometrics.authserver.service.RoleService;
import com.innopolis.innometrics.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("AdminAPI")
public class AdminAPI {

    @Autowired
    ProjectService projectService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/Project")
    public ResponseEntity<ProjectResponse> CreateProject(@RequestBody ProjectRequest project, @RequestHeader(required = false) String Token) {
        System.out.println("Create project method started");
        String UserName = "API";

        if (project == null)
            throw new ValidationException("Not enough data provided");

        if (project.getName() == null)
            throw new ValidationException("Not enough data provided");

        if (projectService.existsByName(project.getName()))
            throw new ValidationException("Username already existed");

        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);


        System.out.println("New project creation...");
        ProjectResponse response = projectService.save(project);
        System.out.println("New project saved...");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/Project")
    public ResponseEntity<ProjectResponse> updateProject(@RequestBody ProjectRequest project, @RequestHeader(required = true) String Token) {
        if (project != null) {
            if(projectService.existsByProjectID(project.getProjectID())){

                ProjectResponse response = projectService.update(project);

                return ResponseEntity.ok(response);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else
            throw new ValidationException("Not enough data provided");
    }

    @GetMapping("/Project")
    public ResponseEntity<ProjectListResponse> getActiveProjects() {
        ProjectListResponse response = projectService.getProjectList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/Users")
    public ResponseEntity<UserListResponse> getActiveUsers(@RequestParam(required = false) String projectId) {

        UserListResponse response = userService.getActiveUsers(projectId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/Users/projects/{UserName}")
    public ResponseEntity<ProjectListResponse> getProjectsByUsername(@PathVariable String UserName) {
        if (!UserName.isEmpty()) {
            User userDetails = userService.findByEmail(UserName);

            if(userDetails == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            ProjectListResponse response =  new ProjectListResponse();
            for (Project p: userDetails.getProjects()) {
                ProjectResponse pTemp = new ProjectResponse();
                pTemp.setName(p.getName());
                pTemp.setProjectID(p.getProjectID());
                pTemp.setIsActive(p.getIsactive());
                response.getProjectList().add(pTemp);
            }


            return ResponseEntity.ok(response);
        } else
            throw new ValidationException("Not enough data provided");
    }


    @GetMapping("/Role/Permissions/{RoleName}")
    public PageListResponse getPages(@PathVariable String RoleName){
        return roleService.getPagesWithIconsForRole(RoleName);
    }

    @GetMapping("/User/Permissions/{UserName}")
    public ResponseEntity<PermissionResponse> getPermissions(@PathVariable String UserName){
        if (!UserName.isEmpty()) {
            User userDetails = userService.findByEmail(UserName);

            if(userDetails == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            PermissionResponse response =  new PermissionResponse();
            for (Permission p: userDetails.getRole().getPermissions()) {
                response.setRole(p.getRole());
                response.addPage(p.getPage());
            }

            return ResponseEntity.ok(response);
        } else
            throw new ValidationException("Not enough data provided");
    }

}
