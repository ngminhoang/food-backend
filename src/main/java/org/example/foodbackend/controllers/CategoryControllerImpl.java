package org.example.foodbackend.controllers;

import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Category;
import org.example.foodbackend.entities.IngradientPercent;
import org.example.foodbackend.services.CategoryService;
import org.example.foodbackend.services.IngradientPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryControllerImpl extends BaseController<Category, Long, CategoryService> implements CategoryController {
    public CategoryControllerImpl(CategoryService service) {
        super(service);
    }
}