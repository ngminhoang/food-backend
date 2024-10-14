package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.foodbackend.entities.Parameter;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ParameterResponseDTO {
    private Long id;
    private Double sumCalories;
    private Double sumProteins;
    private Double sumCarbs;
    private Double sumFibers;
    private Double sumFats;
    private Double sumSatFats;
    private List<Long> recipeIds;

    public ParameterResponseDTO(Parameter parameter) {
        this.id = parameter.getId();
        this.sumCalories = parameter.getSumCalories();
        this.sumProteins = parameter.getSumProteins();
        this.sumCarbs = parameter.getSumCarbs();
        this.sumFibers = parameter.getSumFibers();
        this.sumFats = parameter.getSumFats();
        this.sumSatFats = parameter.getSumSatFats();
        this.recipeIds = parameter.getRecipes().stream()
                .map(recipe -> recipe.getId())
                .collect(Collectors.toList());
    }
}
