package org.example.foodbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.dto.ParameterRequestDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToMany(mappedBy = "parameter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Recipe> recipes;



    public Parameter(ParameterRequestDTO parameterRequestDTO) {
        this.sumCalories = parameterRequestDTO.getSumCalories();
        this.sumProteins = parameterRequestDTO.getSumProteins();
        this.sumCarbs = parameterRequestDTO.getSumCarbs();
        this.sumFibers = parameterRequestDTO.getSumFibers();
        this.sumFats = parameterRequestDTO.getSumFats();
        this.sumSatFats = parameterRequestDTO.getSumSatFats();
    }

    public Parameter(Double calories, Double proteins, Double carbs, Double fibers, Double fats, Double satFats) {
        this.sumCalories = calories;
        this.sumProteins = proteins;
        this.sumCarbs = carbs;
        this.sumFibers = fibers;
        this.sumFats = fats;
        this.sumSatFats = satFats;
    }
}
