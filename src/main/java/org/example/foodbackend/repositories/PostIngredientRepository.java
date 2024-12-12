package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.PostIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostIngredientRepository extends JpaRepository<PostIngredient, Long> {
}
