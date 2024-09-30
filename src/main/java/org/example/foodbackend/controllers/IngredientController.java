package org.example.foodbackend.controllers;

import org.example.foodbackend.entities.Ingredient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
public interface IngredientController {
    ResponseEntity<List<Ingredient>> getAllIngredients();
}
