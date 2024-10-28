package org.example.foodbackend.controllers;

import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.example.foodbackend.entities.dto.ParameterRequestDTO;
import org.example.foodbackend.entities.dto.ParameterResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/parameter")
public interface ParameterController {
    @GetMapping("/filter")
    ResponseEntity<ParameterResponseDTO> getFilter(@RequestParam Double calories,@RequestParam Double proteins,@RequestParam Double carbs,@RequestParam Double fibers,@RequestParam Double fats,@RequestParam Double satFats);
    @GetMapping("/filter-by-body")
    ResponseEntity<ParameterResponseDTO> getFilterByBody(@RequestParam Double weight,@RequestParam Double height,@RequestParam Integer age,@RequestParam String gender,@RequestParam String activityLevel);
}
