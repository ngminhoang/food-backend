package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "ingradient_percent")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngradientPercent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Represents the relationship between ingredients and percentages
    @ManyToMany
    List<Ingradient> ingradients;

    // Additional attributes like percentage can be added here
    Double percentage;
}
