package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "ingredient")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingradient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    Double nuGrams;
    Double nuCalories;
    Double nuProteins;
    Double nuCarbs;
    Double nuFibers;
    Double nuFats;
    Double nuSatFats;
    Integer nuPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category category;

    @ManyToMany(mappedBy = "ingradients")
    List<IngradientPercent> ingradientPercents;

}
