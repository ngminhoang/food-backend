package org.example.foodbackend.controllers.api.mapper;

import org.example.foodbackend.entities.Category;
import org.example.foodbackend.entities.dto.CategoryRequestDTO;
import org.example.foodbackend.entities.dto.CategoryResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements Mapper<Category, CategoryRequestDTO, CategoryResponseDTO> {

    @Override
    public Category toEntity(CategoryRequestDTO requestDto) {
        return Category.builder().id(requestDto.getId()).name(requestDto.getName()).build();
    }

    @Override
    public CategoryResponseDTO toResponse(Category entity) {
        return new CategoryResponseDTO(entity);
    }
}
