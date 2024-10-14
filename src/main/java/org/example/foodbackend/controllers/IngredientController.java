package org.example.foodbackend.controllers;

import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/ingradient")
public interface IngredientController {

    @GetMapping("/get_list")
    ResponseEntity<List<IngradientResponseDTO>> getList();
}
