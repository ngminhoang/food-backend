package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingredient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
public interface IngredientService {
ResponseEntity<List<Ingredient>> getAllIngredients();
}
