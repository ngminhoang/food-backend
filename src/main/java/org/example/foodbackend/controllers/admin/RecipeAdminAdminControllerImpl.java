package org.example.foodbackend.controllers.admin;

import org.example.foodbackend.controllers.base.BaseAdminController;
import org.example.foodbackend.entities.Recipe;
import org.example.foodbackend.services.RecipeService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeAdminAdminControllerImpl extends BaseAdminController<Recipe, Long, RecipeService> implements RecipeAdminController {
    public RecipeAdminAdminControllerImpl(RecipeService service) {
        super(service);
    }
}
