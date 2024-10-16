package org.example.foodbackend.services;

import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.dto.ParameterRequestDTO;
import org.example.foodbackend.entities.dto.ParameterResponseDTO;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

public interface ParameterService extends BaseService<Parameter, Long> {
     ResponseEntity<ParameterResponseDTO> findByProperties(Parameter parameter);
}
