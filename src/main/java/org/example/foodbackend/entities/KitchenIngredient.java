package org.example.foodbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.enums.EUnit;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kitchen_ingredient")
@Entity
public class KitchenIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name_en;
    @Column
    private String name_vi;
    @Column(length = 500)
    private String img_url;
    @Column
    private EUnit unit;
    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore
    private Set<UserIngredient> userIngredients;
}
