package org.example.foodbackend.controllers.api.public_api;

import org.example.foodbackend.entities.dto.IngredientResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/ingredient")
public interface IngredientController {
    @GetMapping("/get_list")
    ResponseEntity<List<IngredientResponseDTO>> getList();

    @GetMapping("/search")
    ResponseEntity<List<IngredientResponseDTO>> searchIngredients(@RequestParam String keyword, @RequestParam int page, @RequestParam int size, @RequestParam String sort, @RequestParam String order);
    @GetMapping("/suggest")
    ResponseEntity<List<String>> suggest(@RequestParam String keyword);
}
