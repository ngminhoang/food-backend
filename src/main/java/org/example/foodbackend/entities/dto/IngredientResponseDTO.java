package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.foodbackend.entities.Ingredient;

import java.util.List;

@Data
@AllArgsConstructor
public class IngredientResponseDTO {
    Long id;

    String name;

    Double nuGrams;
    Double nuCalories;
    Double nuProteins;
    Double nuCarbs;
    Double nuFibers;
    Double nuFats;
    Double nuSatFats;
    Double nuPrice;
    List<String> imgPaths;

    public IngredientResponseDTO(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.nuGrams = ingredient.getNuGrams();
        this.nuCalories = ingredient.getNuCalories();
        this.nuProteins = ingredient.getNuProteins();
        this.nuCarbs = ingredient.getNuCarbs();
        this.nuFibers = ingredient.getNuFibers();
        this.nuFats = ingredient.getNuFats();
        this.nuSatFats = ingredient.getNuSatFats();
        this.nuPrice = ingredient.getNuPrice();
        this.imgPaths = ingredient.getImgPaths();
    }
}
