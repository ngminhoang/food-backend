package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.foodbackend.entities.IngradientPercent;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class IngradientPercentResponseDTO {
    private Long id;
    private Double percentage;
    private String ingradientNames;

    public IngradientPercentResponseDTO(IngradientPercent ingradientPercent) {
        this.id = ingradientPercent.getId();
        this.percentage = ingradientPercent.getPercent();
        this.ingradientNames = ingradientPercent.getIngredient().getName();
    }
}
