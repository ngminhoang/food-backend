package org.example.foodbackend.controllers;

import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.IngradientPercent;
import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.example.foodbackend.services.IngradientPercentService;
import org.example.foodbackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngradientPercentControllerImpl extends BaseController<IngradientPercent, Long, IngradientPercentService> implements IngradientPercentController {
    @Autowired
    public IngradientPercentControllerImpl(IngradientPercentService service) {
        super(service);
    }
}