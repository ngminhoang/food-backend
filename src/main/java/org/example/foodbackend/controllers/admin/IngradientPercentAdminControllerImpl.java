package org.example.foodbackend.controllers.admin;

import org.example.foodbackend.controllers.base.BaseAdminController;
import org.example.foodbackend.entities.IngradientPercent;
import org.example.foodbackend.services.IngradientPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngradientPercentAdminControllerImpl extends BaseAdminController<IngradientPercent, Long, IngradientPercentService> implements IngradientPercentAdminController {
    @Autowired
    public IngradientPercentAdminControllerImpl(IngradientPercentService service) {
        super(service);
    }
}