package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "parameter")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double sumCalories;
    Double sumProteins;
    Double sumCarbs;
    Double sumFibers;
    Double sumFats;
    Double sumSatFats;

    @OneToMany(mappedBy = "parameter")
    List<Recipe> recipes;
}
