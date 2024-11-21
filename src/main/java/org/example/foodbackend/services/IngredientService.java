package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingredient;

import org.example.foodbackend.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientService extends BaseService<Ingredient, Long> {
    ResponseEntity<List<Ingredient>> search(String query, int page, int size, String sort, String order);
    ResponseEntity<List<String>> suggestion(String query);
    Page<Ingredient> findIngredients(String search, Boolean isVerified, int page, int size, String sortOrder);
    void changeVerifiedStatus(Long id);
    Integer getCount();
}
