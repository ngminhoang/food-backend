package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.KitchenIngredient;
import org.example.foodbackend.services.KitchenIngredientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ingredient")
@Tag(name = "FooFi - Kitchen Ingredient")
public class KitchenIngredientControllerImpl extends BaseController<KitchenIngredient, Long, KitchenIngredientService> {

    public KitchenIngredientControllerImpl(KitchenIngredientService service) {
        super(service);
    }
}
