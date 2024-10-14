package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.foodbackend.entities.Recipe;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class RecipeResponseDTO {
    private Long id;
    private Long parameterId;
    private List<IngradientPercentResponseDTO> ingradientPercents;

    public RecipeResponseDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.parameterId = recipe.getParameter().getId();
        this.ingradientPercents = recipe.getIngradientPercents().stream()
                .map(IngradientPercentResponseDTO::new)
                .collect(Collectors.toList());
    }
}
