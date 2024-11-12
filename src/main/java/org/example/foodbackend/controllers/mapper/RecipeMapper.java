package org.example.foodbackend.controllers.mapper;

import org.example.foodbackend.entities.Recipe;
import org.example.foodbackend.entities.dto.RecipeRequestDTO;
import org.example.foodbackend.entities.dto.RecipeResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper implements Mapper<Recipe, RecipeRequestDTO, RecipeResponseDTO> {

    @Override
    public Recipe toEntity(RecipeRequestDTO requestDto) {
        return Recipe.builder()
                .parameter(requestDto.getParameter())
                .id(requestDto.getId())
                .build();
    }

    @Override
    public RecipeResponseDTO toResponse(Recipe entity) {
        return new RecipeResponseDTO(entity);
    }

}
