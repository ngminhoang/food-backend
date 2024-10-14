package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingradient;

import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientService extends BaseService<Ingradient, Long> {
}
