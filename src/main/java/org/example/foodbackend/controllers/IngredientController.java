package org.example.foodbackend.controllers;

import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/ingredient")
public interface IngredientController {

    @GetMapping("/get_list")
    ResponseEntity<List<IngradientResponseDTO>> getList();

    @GetMapping("/search")
    ResponseEntity<List<IngradientResponseDTO>> search(@RequestParam String keyword, @RequestParam int page, @RequestParam int size, @RequestParam String sort, @RequestParam String order);
    @GetMapping("/suggest")
    ResponseEntity<List<String>> suggest(@RequestParam String keyword);

}
