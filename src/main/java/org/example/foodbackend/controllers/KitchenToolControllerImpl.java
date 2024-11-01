package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.services.KitchenToolService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "FooFi - Kitchen Tool")
@RequestMapping("api/tool")
public class KitchenToolControllerImpl extends BaseController<KitchenTool, Long, KitchenToolService> {
    public KitchenToolControllerImpl(KitchenToolService service) {
        super(service);
    }
}
