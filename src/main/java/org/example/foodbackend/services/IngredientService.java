package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingredient;

import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientService extends BaseService<Ingredient, Long> {
    ResponseEntity<List<Ingredient>> search(String query, int page, int size, String sort, String order);
    ResponseEntity<List<String>> suggestion(String query);
}
