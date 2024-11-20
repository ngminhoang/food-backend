package org.example.foodbackend.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KitchenIngredientRequestDTO {
    private Long id;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KitchenIngredientRequestDTO that = (KitchenIngredientRequestDTO) o;
        return quantity == that.quantity && Objects.equals(id, that.id);
    }

    // Override hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, quantity);
    }
}
