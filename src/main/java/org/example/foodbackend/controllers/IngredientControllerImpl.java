package org.example.foodbackend.controllers;

import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.example.foodbackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientControllerImpl implements IngredientController {
    @Autowired
    IngredientService service;

    @Override
    public ResponseEntity<List<IngradientResponseDTO>> getList() {
        return null;
    }

    @Override
    public ResponseEntity<List<IngradientResponseDTO>> search(String keyword, int page, int size, String sort, String order) {
        return ResponseEntity.ok(service.search(keyword, page, size, sort, order).getBody().stream().map(IngradientResponseDTO::new).toList());
    }

    @Override
    public ResponseEntity<List<String>> suggest(String keyword) {
        return service.suggestion(keyword);
    }

}