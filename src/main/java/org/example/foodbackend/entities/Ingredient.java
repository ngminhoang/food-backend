package org.example.foodbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.foodbackend.entities.enums.SearchStatus;

import java.util.List;

@Data
@Entity
@Table(name = "ingredient")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

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
    private Boolean isVerified = false;
    private SearchStatus searchStatus = SearchStatus.PENDING;

    @ElementCollection
    private List<String> imgPaths;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "ingredient_percent_mapping",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "percent_id")
    )
    @JsonIgnore
    @ToString.Exclude
    private List<IngradientPercent> ingredientPercents;  // This will be ignored in the JSON response

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nuPrice=" + nuPrice +
                '}'; // Avoid printing the whole category
    }
}
