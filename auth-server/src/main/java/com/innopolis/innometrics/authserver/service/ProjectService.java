package com.innopolis.innometrics.authserver.service;


import com.innopolis.innometrics.authserver.DTO.ProjectListRequest;
import com.innopolis.innometrics.authserver.DTO.ProjectRequest;
import com.innopolis.innometrics.authserver.entitiy.Project;
import com.innopolis.innometrics.authserver.repository.ProjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

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

    public ProjectRequest create(ProjectRequest detail) {
        Project entity = new Project();
        detail.setProjectID(null);
        BeanUtils.copyProperties(detail, entity, getNullPropertyNames(detail));
        entity = Repository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity, detail);
        return detail;

    }

    public ProjectRequest getById(Integer projectId) {
        Project project = Repository.findByProjectID(projectId);
        ProjectRequest detail = new ProjectRequest();
        BeanUtils.copyProperties(project, detail);
        return detail;
    }

    public ProjectRequest update(ProjectRequest detail) {

        Project entity = Repository.findByProjectID(detail.getProjectID());

        assertNotNull(entity,
                "No project found by this id " + detail.getProjectID());

        detail.setProjectID(null);
        BeanUtils.copyProperties(detail, entity, getNullPropertyNames(detail));
        entity = Repository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity, detail);

        return detail;
    }

    public ProjectListRequest getActiveProjectList() {
        List<Project> result = Repository.findAllActive();
        return convertFromList(result);
    }

    public ProjectListRequest getAllProjectList() {
        List<Project> result = Repository.findAll();
        return convertFromList(result);
    }

    private ProjectListRequest convertFromList(List<Project> projectList) {
        ProjectListRequest response = new ProjectListRequest();
        for (Project p : projectList) {
            ProjectRequest detail = new ProjectRequest();
            BeanUtils.copyProperties(p, detail);
            response.getProjectList().add(detail);
        }
        return response;
    }


    public void delete(Integer projectId) {
        Project entity = Repository.findById(projectId).orElse(null);
        assertNotNull(entity,
                "No profile found by id " + projectId);


        Repository.delete(entity);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set emptyNames = new HashSet();

        for (java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {

            if (src.getPropertyValue(descriptor.getName()) == null) {
                emptyNames.add(descriptor.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }
}
