package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.Recipe;

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
    private List<Recipe> recipes;

    public ParameterResponseDTO(Parameter parameter) {
        try {
            this.id = parameter.getId();
            this.sumCalories = parameter.getSumCalories();
            this.sumProteins = parameter.getSumProteins();
            this.sumCarbs = parameter.getSumCarbs();
            this.sumFibers = parameter.getSumFibers();
            this.sumFats = parameter.getSumFats();
            this.sumSatFats = parameter.getSumSatFats();
            this.recipes = parameter.getRecipes();
        }
        catch (Exception ex){}
    }
}
