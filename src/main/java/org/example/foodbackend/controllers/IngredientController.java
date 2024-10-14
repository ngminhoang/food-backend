package org.example.foodbackend.controllers;

import org.example.foodbackend.entities.Ingradient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IngredientController {
    ResponseEntity<List<Ingradient>> getAllIngredients();

    @PostMapping("/create")
    ResponseEntity<List<Ingradient>> createIngredients(@RequestBody List<Ingradient> ingradients);
}
