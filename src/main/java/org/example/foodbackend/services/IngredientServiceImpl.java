package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok( ingredientRepository.findAll());
    }
}
