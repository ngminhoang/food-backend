package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.Ingredient;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeilisearchIngredient {
    private Long id;
    private String name;
    private String type;
    private Double calo;      // Calories
    private Double protein;
    private Double fat;
    private Double satFat; // Saturated fat
    private Double fiber;
    private Double carb;   // Carbohydrates

    public MeilisearchIngredient(Ingredient ingredient) {
        this.calo = ingredient.getNuCalories();
        this.name = ingredient.getName();
        this.id = ingredient.getId();
        this.protein = ingredient.getNuProteins();
        this.fat = ingredient.getNuFats();
        this.fiber = ingredient.getNuFibers();
        this.carb = ingredient.getNuCarbs();
        this.satFat = ingredient.getNuSatFats();
    }
}
