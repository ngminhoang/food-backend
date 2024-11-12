package org.example.foodbackend.controllers.mapper;


import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.entities.dto.IngredientResponseDTO;
import org.example.foodbackend.entities.dto.IngredientRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper implements Mapper<Ingredient, IngredientRequestDTO, IngredientResponseDTO> {


    @Override
    public Ingredient toEntity(IngredientRequestDTO requestDto) {
        return Ingredient.builder()
                .nuCarbs(requestDto.getNuCarbs())
                .nuFats(requestDto.getNuFats())
                .nuSatFats(requestDto.getNuSatFats())
                .name(requestDto.getName())
                .nuFibers(requestDto.getNuFibers())
                .nuCalories(requestDto.getNuCalories())
                .nuGrams(requestDto.getNuGrams())
                .build();
    }

    @Override
    public IngredientResponseDTO toResponse(Ingredient entity) {
        return new IngredientResponseDTO(entity);
    }
}
