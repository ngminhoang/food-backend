package org.example.foodbackend.controllers.api.admin_api;

import org.example.foodbackend.controllers.api.base.BaseAdminController;
import org.example.foodbackend.controllers.api.mapper.IngredientPercentMapper;
import org.example.foodbackend.entities.IngradientPercent;
import org.example.foodbackend.entities.dto.IngradientPercentResponseDTO;
import org.example.foodbackend.entities.dto.IngredientPercentRequestDTO;
import org.example.foodbackend.services.IngradientPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngradientPercentAdminControllerImpl extends BaseAdminController<IngradientPercent, Long, IngradientPercentService, IngredientPercentRequestDTO, IngradientPercentResponseDTO> implements IngradientPercentAdminController {
    @Autowired
    public IngradientPercentAdminControllerImpl(IngradientPercentService service, IngredientPercentMapper mapper) {
        super(service, mapper);
    }
}