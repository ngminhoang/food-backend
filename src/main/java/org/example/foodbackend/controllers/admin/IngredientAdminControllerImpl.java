package org.example.foodbackend.controllers.admin;

import org.example.foodbackend.controllers.base.BaseAdminController;
import org.example.foodbackend.controllers.mapper.IngredientMapper;
import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.entities.dto.IngredientResponseDTO;
import org.example.foodbackend.entities.dto.IngredientRequestDTO;
import org.example.foodbackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IngredientAdminControllerImpl extends BaseAdminController<Ingredient, Long, IngredientService, IngredientRequestDTO, IngredientResponseDTO> implements IngredientAdminController {
    @Autowired
    public IngredientAdminControllerImpl(IngredientService service, IngredientMapper mapper) {
        super(service, mapper);
    }
}
