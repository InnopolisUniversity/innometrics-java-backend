package com.innopolis.innometrics.configservice.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryListResponse implements Serializable {
    private List<CategoryResponse> categoryList;

    public CategoryListResponse() {
        categoryList = new ArrayList<>();
    }

    public CategoryListResponse(List<CategoryResponse> categoryList) {
        this.categoryList = categoryList;
    }

    public List<CategoryResponse> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryResponse> categoryList) {
        this.categoryList = categoryList;
    }
}
