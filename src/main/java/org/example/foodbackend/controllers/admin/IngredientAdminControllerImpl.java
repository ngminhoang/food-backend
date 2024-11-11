package org.example.foodbackend.controllers.admin;

import org.example.foodbackend.controllers.base.BaseAdminController;
import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.example.foodbackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class IngredientAdminControllerImpl extends BaseAdminController<Ingradient, Long, IngredientService> implements IngredientAdminController {
    @Autowired
    public IngredientAdminControllerImpl(IngredientService service) {
        super(service);
    }
}
