package com.innopolis.innometrics.activitiescollector.service;

import com.innopolis.innometrics.activitiescollector.DTO.*;
import com.innopolis.innometrics.activitiescollector.entity.*;
import com.innopolis.innometrics.activitiescollector.entity.Process;
import com.innopolis.innometrics.activitiescollector.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.SimpleDateFormat;
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
        act.setCatname(categoryRequest.getCategoryname());
        act.setIsactive("Y");
        act.setCreatedby(UserName);

        act = actCategoryRepository.save(act);
        CategoryResponse response = new CategoryResponse();

        response.setCatid(act.getCatid());
        response.setCatname(act.getCatname());
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
        act.setCatname(categoryRequest.getCategoryname());
        act.setIsactive(categoryRequest.getIsactive());
        act.setUpdateby(UserName);
        act.setLastupdate(new Date());

        act = actCategoryRepository.save(act);

        CategoryResponse response = new CategoryResponse();

        response.setCatid(act.getCatid());
        response.setCatname(act.getCatname());
        response.setIsactive(act.getIsactive());
        response.setCreatedby(act.getCreatedby());
        response.setCreationdate(act.getCreationdate());
        response.setUpdateby(act.getUpdateby());
        response.setLastupdate(act.getLastupdate());

        return response;
    }

    public CategoryListResponse getAllCategories() {
        List<ActCategories> categories = new ArrayList<>();

        categories = actCategoryRepository.findAll();

        if (categories.size() == 0) return null;

        CategoryListResponse response = new CategoryListResponse();

        ModelMapper modelMapper = new ModelMapper();
        for (ActCategories cat : categories) {
            CategoryResponse catDTO = modelMapper.map(cat, CategoryResponse.class);
            response.getCategories().add(catDTO);
        }

        return response;
    }

    public CategoryResponse getCategoryById(Integer categoryid) {
        List<ActCategories> act = new ArrayList<>();

        act = actCategoryRepository.findAllById(Collections.singleton(categoryid));

        if (act.size() == 0) return null;

        CategoryResponse response = new CategoryResponse();

        response.setCatid(act.get(0).getCatid());
        response.setCatname(act.get(0).getCatname());
        response.setIsactive(act.get(0).getIsactive());
        response.setCreatedby(act.get(0).getCreatedby());
        response.setCreationdate(act.get(0).getCreationdate());
        response.setUpdateby(act.get(0).getUpdateby());
        response.setLastupdate(act.get(0).getLastupdate());

        return response;
    }

    public Boolean exitsByCategoryName(String categoryname) {
        return actCategoryRepository.existsByCatname(categoryname);
    }


    public AppCategoryResponse CreateAppCategory(AppCategoryRequest appcategoryRequest, String UserName) {
        ActAppxCategory app = new ActAppxCategory();
        app.setAppname(appcategoryRequest.getAppname());
        app.setCatid(appcategoryRequest.getCatid());
        app.setIsactive("Y");
        app.setCreatedby(UserName);
        app.setCreationdate(new Date());

        app = actAppxCategoryRepository.save(app);
        AppCategoryResponse response = new AppCategoryResponse();

        response.setAppid(app.getAppid());
        response.setAppname(app.getAppname());
        response.setCatid(app.getCatid());
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
        app.setCatid(appcategoryRequest.getCatid());
        app.setIsactive(appcategoryRequest.getIsactive());
        app.setUpdateby(UserName);
        app.setLastupdate(new Date());

        app = actAppxCategoryRepository.save(app);

        AppCategoryResponse response = new AppCategoryResponse();

        response.setAppid(app.getAppid());
        response.setAppname(app.getAppname());
        response.setCatid(app.getCatid());
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
        response.setCatid(app.get(0).getCatid());
        response.setIsactive(app.get(0).getIsactive());
        response.setCreatedby(app.get(0).getCreatedby());
        response.setCreationdate(app.get(0).getCreationdate());
        response.setUpdateby(app.get(0).getUpdateby());
        response.setLastupdate(app.get(0).getLastupdate());

        return response;
    }



    public CategoriesTimeReportResponse getTimeReport(TimeReportRequest request) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<ICategoriesReport> result = actCategoryRepository.getTimeReport(
                request.getProjectID(),
                request.getEmail(),
                (request.getMin_Date() != null ? formatter.format(request.getMin_Date()) : null),
                (request.getMax_Date() != null ? formatter.format(request.getMax_Date()) : null));

        CategoriesTimeReportResponse response = new CategoriesTimeReportResponse();
        for (ICategoriesReport a : result) {
            CategoriesReport temp = new CategoriesReport();

            temp.setCatname(a.getCatname());
            temp.setCatdescription(a.getCatdescription());
            temp.setTimeused(a.getTimeused());

            response.getReport().add(temp);
        }

        return response;
    }
}
