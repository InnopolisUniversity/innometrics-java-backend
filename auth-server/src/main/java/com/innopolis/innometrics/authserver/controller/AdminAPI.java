package com.innopolis.innometrics.authserver.controller;

import com.innopolis.innometrics.authserver.DTO.*;
import com.innopolis.innometrics.authserver.config.JwtToken;
import com.innopolis.innometrics.authserver.exceptions.ValidationException;
import com.innopolis.innometrics.authserver.service.ProjectService;
import com.innopolis.innometrics.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("AdminAPI")
public class AdminAPI {

    @Autowired
    ProjectService projectService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserService userService;

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
}
