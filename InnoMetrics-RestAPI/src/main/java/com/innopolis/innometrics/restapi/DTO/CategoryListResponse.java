package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CategoryListResponse implements Serializable {
    private List<CategoryResponse> categories;

    public CategoryListResponse() {
        categories = new ArrayList<>();
    }

    public CategoryListResponse(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }
}
