package org.example.foodbackend.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KitchenIngredientRequestDTO {
    private Long id;
    private int quantity;
}
