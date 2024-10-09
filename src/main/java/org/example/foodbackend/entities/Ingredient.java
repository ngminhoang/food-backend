package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@Data
@Entity
@Table(name = "ingredient")
@PlanningEntity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category category;

    String name;

    // Define value ranges for planning variables
    @PlanningVariable(valueRangeProviderRefs = "gramRange")
    Double nuGrams;

    @PlanningVariable(valueRangeProviderRefs = "calorieRange")
    Double nuCalories;

    @PlanningVariable(valueRangeProviderRefs = "proteinRange")
    Double nuProteins;

    @PlanningVariable(valueRangeProviderRefs = "carbRange")
    Double nuCarbs;

    @PlanningVariable(valueRangeProviderRefs = "fiberRange")
    Double nuFibers;

    @PlanningVariable(valueRangeProviderRefs = "fatRange")
    Double nuFats;

    @PlanningVariable(valueRangeProviderRefs = "satFatRange")
    Double nuSatFats;

    @PlanningVariable(valueRangeProviderRefs = "priceRange")
    Integer nuPrice;

    public Ingredient(String name, Double nuGrams, Double nuCalories, Double nuProteins, Double nuCarbs, Double nuFibers, Double nuFats, Double nuSatFats, Integer nuPrice) {
        this.name = name;
        this.nuGrams = nuGrams;
        this.nuCalories = nuCalories;
        this.nuProteins = nuProteins;
        this.nuCarbs = nuCarbs;
        this.nuFibers = nuFibers;
        this.nuFats = nuFats;
        this.nuSatFats = nuSatFats;
        this.nuPrice = nuPrice;
    }

    public Ingredient() {
    }
}
