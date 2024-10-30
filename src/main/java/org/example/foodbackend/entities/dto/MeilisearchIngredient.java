package org.example.foodbackend.entities.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.Ingradient;

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

    public MeilisearchIngredient(Ingradient ingradient) {
        this.calo = ingradient.getNuCalories();
        this.name = ingradient.getName();
        this.id = ingradient.getId();
        this.protein = ingradient.getNuProteins();
        this.fat = ingradient.getNuFats();
        this.fiber = ingradient.getNuFibers();
        this.carb = ingradient.getNuCarbs();
        this.satFat = ingradient.getNuSatFats();
    }
}
