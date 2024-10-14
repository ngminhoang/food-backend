package org.example.foodbackend.controllers;

import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.Recipe;
import org.example.foodbackend.services.IngredientService;
import org.example.foodbackend.services.RecipeService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeControllerImpl extends BaseController<Recipe, Long, RecipeService> implements RecipeController {
    public RecipeControllerImpl(RecipeService service) {
        super(service);
    }
}
