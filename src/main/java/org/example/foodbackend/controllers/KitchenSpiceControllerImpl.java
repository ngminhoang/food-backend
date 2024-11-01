package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.KitchenSpice;
import org.example.foodbackend.services.KitchenSpiceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "FooFi - Kitchen Spice")
@RequestMapping("api/spice")
public class KitchenSpiceControllerImpl extends BaseController<KitchenSpice, Long, KitchenSpiceService> {
    public KitchenSpiceControllerImpl(KitchenSpiceService service) {
        super(service);
    }
}
