package org.example.foodbackend.controllers;

import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.services.ParameterService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParameterControllerImpl extends BaseController<Parameter, Long, ParameterService> implements ParameterController {
    public ParameterControllerImpl(ParameterService service) {
        super(service);
    }
}
