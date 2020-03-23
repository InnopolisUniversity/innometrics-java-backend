package com.innopolis.innometrics.configservice.controller;

import com.innopolis.innometrics.configservice.DTO.CategoryListResponse;
import com.innopolis.innometrics.configservice.DTO.CategoryRequest;
import com.innopolis.innometrics.configservice.DTO.CategoryResponse;
import com.innopolis.innometrics.configservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
//@CrossOrigin
@RequestMapping("Categorization")
public class Categorization {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/Category")
    public ResponseEntity<CategoryListResponse> getCategories(@RequestHeader(required = false) String Token) {
        CategoryListResponse response = categoryService.getCategoriesList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/Category/{catId}")
    public ResponseEntity<CategoryResponse> getCategoriesById(@PathVariable Integer catId, @RequestHeader(required = false) String Token) {
        CategoryResponse response = categoryService.getCategoryByID(catId);
        if(response == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/Category")
    public ResponseEntity<CategoryResponse> CreateCategory(@RequestBody CategoryRequest category, @RequestHeader(required = false) String Token) {
        CategoryResponse response = categoryService.save(category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/Category")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody  CategoryRequest category, @RequestHeader(required = false) String Token) {
        CategoryResponse response = categoryService.save(category);
        return ResponseEntity.ok(response);
    }

}
