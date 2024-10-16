package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    // Bi-directional Many-to-Many relationship with Ingredient
    @ManyToMany
    @JoinTable(
            name = "ingredient_percent_mapping",
            joinColumns = @JoinColumn(name = "percent_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingradient> ingredients;

}
