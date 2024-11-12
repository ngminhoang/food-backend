package org.example.foodbackend.controllers.admin;

import org.example.foodbackend.controllers.base.BaseAdminController;
import org.example.foodbackend.controllers.mapper.RecipeMapper;
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
