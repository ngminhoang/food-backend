package org.example.foodbackend.entities.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class IngredientPercentRequestDTO {
    private Long id;
    private Double percentage;
    private String ingradientNames;
}
