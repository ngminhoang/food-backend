package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingredient;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientService {
    ResponseEntity<List<Ingredient>> getAllIngredients();

    ResponseEntity<List<Ingredient>> createIngredients(List<Ingredient> ingredients);
}
