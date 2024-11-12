package org.example.foodbackend.entities.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientRequestDTO {
    String name;

    Double nuGrams;
    Double nuCalories;
    Double nuProteins;
    Double nuCarbs;
    Double nuFibers;
    Double nuFats;
    Double nuSatFats;
    Double nuPrice;
}
