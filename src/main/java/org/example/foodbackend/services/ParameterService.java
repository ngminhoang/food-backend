package org.example.foodbackend.services;

import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.dto.ParameterResponseDTO;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

public interface ParameterService extends BaseService<Parameter, Long> {
     ResponseEntity<ParameterResponseDTO> findByNutrientProperties(Parameter parameter);
     Integer getCount();
     ResponseEntity<ParameterResponseDTO> findByBodyProperties(Double weight, Double height, Integer age, String gender, String activityLevel);
}
