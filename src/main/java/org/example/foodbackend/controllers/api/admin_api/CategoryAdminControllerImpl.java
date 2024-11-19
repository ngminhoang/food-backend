package org.example.foodbackend.controllers.api.admin_api;

import org.example.foodbackend.controllers.api.base.BaseAdminController;
import org.example.foodbackend.controllers.api.mapper.CategoryMapper;
import org.example.foodbackend.entities.Category;
import org.example.foodbackend.entities.dto.CategoryRequestDTO;
import org.example.foodbackend.entities.dto.CategoryResponseDTO;
import org.example.foodbackend.services.CategoryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryAdminControllerImpl extends BaseAdminController<Category, Long, CategoryService, CategoryRequestDTO, CategoryResponseDTO> implements CategoryAdminController {
    public CategoryAdminControllerImpl(CategoryService service, CategoryMapper categoryMapper) {
        super(service, categoryMapper);
    }
}