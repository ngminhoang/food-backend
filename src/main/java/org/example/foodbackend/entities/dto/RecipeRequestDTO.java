package org.example.foodbackend.entities.dto;

import lombok.Builder;
import lombok.Data;
import org.example.foodbackend.entities.Parameter;

import java.util.List;
@Data
@Builder
public class RecipeRequestDTO {
    private Long id;
    private Parameter parameter;
}
