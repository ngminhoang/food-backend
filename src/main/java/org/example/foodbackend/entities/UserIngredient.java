package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_ingredients")
public class UserIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private KitchenIngredient ingredient;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;
    @Column
    private int quantity;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
}
