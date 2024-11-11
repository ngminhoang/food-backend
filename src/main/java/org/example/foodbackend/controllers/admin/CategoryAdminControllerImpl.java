package org.example.foodbackend.controllers.admin;

import org.example.foodbackend.controllers.base.BaseAdminController;
import org.example.foodbackend.entities.Category;
import org.example.foodbackend.services.CategoryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryAdminControllerImpl extends BaseAdminController<Category, Long, CategoryService> implements CategoryAdminController {
    public CategoryAdminControllerImpl(CategoryService service) {
        super(service);
    }
}