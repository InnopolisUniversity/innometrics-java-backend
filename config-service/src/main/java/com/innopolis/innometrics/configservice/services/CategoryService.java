package com.innopolis.innometrics.configservice.services;

import com.innopolis.innometrics.configservice.DTO.CategoryListResponse;
import com.innopolis.innometrics.configservice.DTO.CategoryRequest;
import com.innopolis.innometrics.configservice.DTO.CategoryResponse;
import com.innopolis.innometrics.configservice.entities.Category;
import com.innopolis.innometrics.configservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository catRepo;

    public CategoryListResponse getCategoriesList() {
        List<Category> catList = catRepo.findAll();
        CategoryListResponse myResponse = new CategoryListResponse();

        for (Category c : catList) {
            CategoryResponse myCat = new CategoryResponse();
            myCat.setCatid(c.getCatid());
            myCat.setCat_name(c.getCat_name());
            myCat.setCat_description(c.getCat_description());
            myCat.setCreatedby(c.getCreatedby());
            myCat.setCreationdate(c.getCreationdate());
            myCat.setLastupdate(c.getLastupdate());
            myCat.setUpdateby(c.getUpdateby());
            myResponse.getCategoryList().add(myCat);
        }

        return myResponse;
    }

    public CategoryResponse getCategoryByID(Integer catId) {

        if (!catRepo.existsById(catId)) return null;

        Category cat = catRepo.findById(catId).get();
        CategoryResponse myCat = new CategoryResponse();
        myCat.setCatid(cat.getCatid());
        myCat.setCat_name(cat.getCat_name());
        myCat.setCat_description(cat.getCat_description());
        myCat.setCreatedby(cat.getCreatedby());
        myCat.setCreationdate(cat.getCreationdate());
        myCat.setLastupdate(cat.getLastupdate());
        myCat.setUpdateby(cat.getUpdateby());

        return myCat;
    }

    public CategoryResponse save(CategoryRequest category) {
        Category myCat = new Category();
        if (existsByName(category.getCat_name())) {
            myCat = catRepo.findById(category.getCatid()).get();
        }

        myCat.setCat_name(category.getCat_name());
        myCat.setCat_description(category.getCat_description());

        catRepo.save(myCat);

        CategoryResponse response = new CategoryResponse();

        response.setCatid(myCat.getCatid());
        response.setCat_name(myCat.getCat_name());
        response.setCat_description(myCat.getCat_description());
        response.setCreatedby(myCat.getCreatedby());
        response.setCreationdate(myCat.getCreationdate());
        response.setLastupdate(myCat.getLastupdate());
        response.setUpdateby(myCat.getUpdateby());

        return response;
    }

    private Category findByCat_name(String cat_name) {
        return catRepo.findBycatname(cat_name);
    }

    public Boolean existsByName(String cat_name) {
        return catRepo.existsBycatname(cat_name);
    }
}
