package org.example.foodbackend.controllers;

import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/public")
public class IngredientControllerImpl implements IngredientController {

    @Autowired
    private IngredientService ingredientRepository;

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<Ingradient>> getAllIngredients() {
        return ingredientRepository.getAllIngredients();
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<List<Ingradient>> createIngredients(@RequestBody List<Ingradient> ingradients) {
        return ingredientRepository.createIngredients(ingradients);
    }
}
