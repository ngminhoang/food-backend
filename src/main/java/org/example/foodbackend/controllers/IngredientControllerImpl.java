package org.example.foodbackend.controllers;

import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.example.foodbackend.services.IngredientService;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class IngredientControllerImpl extends BaseController<Ingradient, Long, IngredientService> implements IngredientController {
    @Autowired
    public IngredientControllerImpl(IngredientService service) {
        super(service);
    }

    @Override
    public ResponseEntity<List<IngradientResponseDTO>> getList() {
        return null;
    }

    @Override
    public ResponseEntity<List<IngradientResponseDTO>> search(String keyword, int page, int size) {
        return ResponseEntity.ok(service.search(keyword, page, size).getBody().stream().map(IngradientResponseDTO::new).toList());
    }
}
