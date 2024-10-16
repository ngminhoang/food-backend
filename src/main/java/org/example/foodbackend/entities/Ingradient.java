package org.example.foodbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.Category;
import org.example.foodbackend.entities.IngradientPercent;

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
    private Long id;

    @Column(nullable = false)
    private String name;

    private Double nuGrams;
    private Double nuCalories;
    private Double nuProteins;
    private Double nuCarbs;
    private Double nuFibers;
    private Double nuFats;
    private Double nuSatFats;
    private Double nuPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnore
    private Category category;  // This will include only the Category object without its ingredients

    @ManyToMany
    @JoinTable(
            name = "ingredient_percent_mapping",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "percent_id")
    )
    @JsonIgnore
    private List<IngradientPercent> ingredientPercents;  // This will be ignored in the JSON response

}
