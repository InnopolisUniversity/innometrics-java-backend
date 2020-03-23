package com.innopolis.innometrics.authserver.service;


import com.innopolis.innometrics.authserver.DTO.ProjectListResponse;
import com.innopolis.innometrics.authserver.DTO.ProjectRequest;
import com.innopolis.innometrics.authserver.DTO.ProjectResponse;
import com.innopolis.innometrics.authserver.entitiy.Project;
import com.innopolis.innometrics.authserver.entitiy.User;
import com.innopolis.innometrics.authserver.repository.ProjectRepository;
import com.innopolis.innometrics.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectService {
    @Autowired
    private ProjectRepository Repository;

    public Boolean existsByName(String projectName) {
        return Repository.existsByName(projectName);
    }

    public Boolean existsByProjectID(Integer projectId) {
        return Repository.existsByProjectID(projectId);
    }

    public ProjectResponse save(ProjectRequest project) {
        ProjectResponse response = new ProjectResponse();

        Project myProject = new Project();
        myProject.setProjectID(project.getProjectID());
        myProject.setName(project.getName());
        myProject.setIsactive("Y");
        Repository.save(myProject);
        response.setProjectID(myProject.getProjectID());
        response.setName(myProject.getName());
        response.setIsActive(myProject.getIsactive());
        return response;
    }


    public ProjectResponse update(ProjectRequest project) {
        ProjectResponse response = new ProjectResponse();

        Project myProject = Repository.findByProjectID(project.getProjectID());

        myProject.setName(project.getName());
        myProject.setIsactive(project.getIsActive());

        Repository.save(myProject);
        response.setProjectID(myProject.getProjectID());
        response.setName(myProject.getName());
        response.setIsActive(myProject.getIsactive());
        return response;
    }

    public ProjectListResponse getProjectList() {
        List<Project> result = Repository.findAllActive();
        ProjectListResponse response = new ProjectListResponse();
        for (Project p : result) {
            ProjectResponse temp = new ProjectResponse();
            temp.setProjectID(p.getProjectID());
            temp.setName(p.getName());
            temp.setIsActive(p.getIsactive());
            response.getProjectList().add(temp);
        }
        return response;
    }
}
