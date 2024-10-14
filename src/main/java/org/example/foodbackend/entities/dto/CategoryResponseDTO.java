package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.foodbackend.entities.Category;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private List<String> ingradientNames;

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.ingradientNames = category.getIngradients().stream()
                .map(ingradient -> ingradient.getName())
                .collect(Collectors.toList());
    }
}
