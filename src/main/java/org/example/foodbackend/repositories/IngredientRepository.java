package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
