package org.example.foodbackend.controllers;

import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.dto.ParameterRequestDTO;
import org.example.foodbackend.entities.dto.ParameterResponseDTO;
import org.example.foodbackend.services.ParameterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParameterControllerImpl extends BaseController<Parameter, Long, ParameterService> implements ParameterController {
    public ParameterControllerImpl(ParameterService service) {
        super(service);
    }

    @Override
    public ResponseEntity<ParameterResponseDTO> getFilter(Double calories, Double proteins, Double carbs, Double fibers, Double fats, Double satFats) {
        return service.findByProperties(new Parameter(calories,proteins,carbs,fibers,fats,satFats));
    }
}
