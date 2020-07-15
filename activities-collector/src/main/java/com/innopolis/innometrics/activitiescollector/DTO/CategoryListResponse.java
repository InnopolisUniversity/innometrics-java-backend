package com.innopolis.innometrics.activitiescollector.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
