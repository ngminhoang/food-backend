package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingradient;
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
    public ResponseEntity<List<Ingradient>> getAllIngredients() {
        return ResponseEntity.ok( ingredientRepository.findAll());
    }

    @Override
    public ResponseEntity<List<Ingradient>> createIngredients(List<Ingradient> ingradients){
        return ResponseEntity.ok( ingredientRepository.saveAll(ingradients));
    }
}
