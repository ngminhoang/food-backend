package org.example.foodbackend.services;

import org.example.foodbackend.entities.Category;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService extends BaseService<Category, Long> {
    Page<Category> findCategories(String search, int page, int size);
}
