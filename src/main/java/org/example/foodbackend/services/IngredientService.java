package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingradient;

import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientService extends BaseService<Ingradient, Long> {
    ResponseEntity<List<Ingradient>> search(String query, int page, int size, String sort, String order);
    ResponseEntity<List<String>> suggestion(String query);
}
