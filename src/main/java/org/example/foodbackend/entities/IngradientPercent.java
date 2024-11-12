package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ingredient_percent")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngradientPercent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double percent;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;  // This will hold the associated ingredient

    public IngradientPercent(Double percent, Ingredient ingredient) {
        this.percent = percent;
        this.ingredient = ingredient;
    }
}
