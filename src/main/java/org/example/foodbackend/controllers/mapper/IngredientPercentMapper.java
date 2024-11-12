package org.example.foodbackend.controllers.mapper;

import org.example.foodbackend.entities.IngradientPercent;
import org.example.foodbackend.entities.dto.IngradientPercentResponseDTO;
import org.example.foodbackend.entities.dto.IngredientPercentRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class IngredientPercentMapper implements Mapper<IngradientPercent, IngredientPercentRequestDTO, IngradientPercentResponseDTO> {

    @Override
    public IngradientPercent toEntity(IngredientPercentRequestDTO requestDto) {
        return null;
    }

    @Override
    public IngradientPercentResponseDTO toResponse(IngradientPercent entity) {
        return null;
    }
}
