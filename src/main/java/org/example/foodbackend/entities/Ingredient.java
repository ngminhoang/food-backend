package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category category;
    String name;
    Double nuGrams;
    Double nuCalories;
    Double nuProteins;
    Double nuCarbs;
    Double nuFibers;
    Double nuFats;
    Double nuSatFats;
    Integer nuPrice;
}
