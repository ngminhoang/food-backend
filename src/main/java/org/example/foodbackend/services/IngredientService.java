package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingradient;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientService {
    ResponseEntity<List<Ingradient>> getAllIngredients();

    ResponseEntity<List<Ingradient>> createIngredients(List<Ingradient> ingradients);
}
