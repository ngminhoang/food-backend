package org.example.foodbackend.controllers;

import org.example.foodbackend.entities.Ingredient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IngredientController {
    ResponseEntity<List<Ingredient>> getAllIngredients();

    @PostMapping("/create")
    ResponseEntity<List<Ingredient>> createIngredients(@RequestBody List<Ingredient> ingredients);
}
