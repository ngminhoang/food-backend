package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.KitchenIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenIngredientRepository extends JpaRepository<KitchenIngredient, Long> {
}
