package com.innopolis.innometrics.activitiescollector.service;

import com.innopolis.innometrics.activitiescollector.DTO.*;
import com.innopolis.innometrics.activitiescollector.entity.*;
import com.innopolis.innometrics.activitiescollector.entity.Process;
import com.innopolis.innometrics.activitiescollector.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    ActCategoryRepository actCategoryRepository;

    @Autowired
    ActAppxCategoryRepository actAppxCategoryRepository;

    public CategoryResponse CreateCategory(CategoryRequest categoryRequest, String UserName) {
        ActCategories act = new ActCategories();
        act.setCategoryname(categoryRequest.getCategoryname());
        act.setIsactive("Y");
        act.setCreatedby(UserName);

        act = actCategoryRepository.save(act);
        CategoryResponse response = new CategoryResponse();

        response.setCategoryid(act.getCategoryid());
        response.setCategoryname(act.getCategoryname());
        response.setIsactive(act.getIsactive());
        response.setCreatedby(act.getCreatedby());
        response.setCreationdate(act.getCreationdate());
        response.setUpdateby(act.getUpdateby());
        response.setLastupdate(act.getLastupdate());

        return response;
    }

    public CategoryResponse UpdateCategory(CategoryRequest categoryRequest, String UserName) {
        ActCategories act;

        act = actCategoryRepository.getOne(categoryRequest.getCategoryid());
        act.setCategoryname(categoryRequest.getCategoryname());
        act.setIsactive(categoryRequest.getIsactive());
        act.setUpdateby(UserName);
        act.setLastupdate(new Date());

        act = actCategoryRepository.save(act);

        CategoryResponse response = new CategoryResponse();

        response.setCategoryid(act.getCategoryid());
        response.setCategoryname(act.getCategoryname());
        response.setIsactive(act.getIsactive());
        response.setCreatedby(act.getCreatedby());
        response.setCreationdate(act.getCreationdate());
        response.setUpdateby(act.getUpdateby());
        response.setLastupdate(act.getLastupdate());

        return response;
    }

    public CategoryResponse getCategoryById(Integer categoryid) {
        List<ActCategories> act = new ArrayList<>();

        act = actCategoryRepository.findAllById(Collections.singleton(categoryid));

        if (act.size() == 0) return null;

        CategoryResponse response = new CategoryResponse();

        response.setCategoryid(act.get(0).getCategoryid());
        response.setCategoryname(act.get(0).getCategoryname());
        response.setIsactive(act.get(0).getIsactive());
        response.setCreatedby(act.get(0).getCreatedby());
        response.setCreationdate(act.get(0).getCreationdate());
        response.setUpdateby(act.get(0).getUpdateby());
        response.setLastupdate(act.get(0).getLastupdate());

        return response;
    }

    public Boolean exitsByCategoryName(String categoryname) {
        return actCategoryRepository.existsByCategoryname(categoryname);
    }


    public AppCategoryResponse CreateAppCategory(AppCategoryRequest appcategoryRequest, String UserName) {
        ActAppxCategory app = new ActAppxCategory();
        app.setAppname(appcategoryRequest.getAppname());
        app.setCategoryid(appcategoryRequest.getCategoryid());
        app.setIsactive("Y");
        app.setCreatedby(UserName);
        app.setCreationdate(new Date());

        app = actAppxCategoryRepository.save(app);
        AppCategoryResponse response = new AppCategoryResponse();

        response.setAppid(app.getAppid());
        response.setAppname(app.getAppname());
        response.setCategoryid(app.getCategoryid());
        response.setIsactive(app.getIsactive());
        response.setCreatedby(app.getCreatedby());
        response.setCreationdate(app.getCreationdate());
        response.setUpdateby(app.getUpdateby());
        response.setLastupdate(app.getLastupdate());

        return response;
    }

    public AppCategoryResponse UpdateAppCategory(AppCategoryRequest appcategoryRequest, String UserName) {
        ActAppxCategory app;

        app = actAppxCategoryRepository.getOne(appcategoryRequest.getAppid());
        app.setAppname(appcategoryRequest.getAppname());
        app.setCategoryid(appcategoryRequest.getCategoryid());
        app.setIsactive(appcategoryRequest.getIsactive());
        app.setUpdateby(UserName);
        app.setLastupdate(new Date());

        app = actAppxCategoryRepository.save(app);

        AppCategoryResponse response = new AppCategoryResponse();

        response.setAppid(app.getAppid());
        response.setAppname(app.getAppname());
        response.setCategoryid(app.getCategoryid());
        response.setIsactive(app.getIsactive());
        response.setCreatedby(app.getCreatedby());
        response.setCreationdate(app.getCreationdate());
        response.setUpdateby(app.getUpdateby());
        response.setLastupdate(app.getLastupdate());

        return response;
    }

    public AppCategoryResponse getAppCategoryById(Integer appid) {
        List<ActAppxCategory> app = new ArrayList<>();

        app = actAppxCategoryRepository.findAllById(Collections.singleton(appid));

        AppCategoryResponse response = new AppCategoryResponse();
        if (app.size() == 0) return null;
        response.setAppid(app.get(0).getAppid());
        response.setAppname(app.get(0).getAppname());
        response.setCategoryid(app.get(0).getCategoryid());
        response.setIsactive(app.get(0).getIsactive());
        response.setCreatedby(app.get(0).getCreatedby());
        response.setCreationdate(app.get(0).getCreationdate());
        response.setUpdateby(app.get(0).getUpdateby());
        response.setLastupdate(app.get(0).getLastupdate());

        return response;
    }
}
