package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.enums.EUnit;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenIngredientResponseDTO {
    private Long id;
    private String name_en;
    private String name_vi;
    private String img_url;
    private EUnit unit;
    private int quantity;
}
