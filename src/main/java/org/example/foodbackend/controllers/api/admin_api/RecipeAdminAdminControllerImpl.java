package org.example.foodbackend.controllers.api.admin_api;

import org.example.foodbackend.controllers.api.base.BaseAdminController;
import org.example.foodbackend.controllers.api.mapper.RecipeMapper;
import org.example.foodbackend.entities.Recipe;
import org.example.foodbackend.entities.dto.RecipeRequestDTO;
import org.example.foodbackend.entities.dto.RecipeResponseDTO;
import org.example.foodbackend.services.RecipeService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeAdminAdminControllerImpl extends BaseAdminController<Recipe, Long, RecipeService, RecipeRequestDTO, RecipeResponseDTO> implements RecipeAdminController {
    public RecipeAdminAdminControllerImpl(RecipeService service, RecipeMapper mapper) {
        super(service, mapper);
    }
}
