package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.dto.FlaskResponseDTO;

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

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingradient ingredient;  // This will hold the associated ingredient

    public IngradientPercent(Double percent, Ingradient ingredient) {
        this.percent = percent;
        this.ingredient = ingredient;
    }
}
