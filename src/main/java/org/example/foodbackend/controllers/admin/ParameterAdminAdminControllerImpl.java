package org.example.foodbackend.controllers.admin;

import org.example.foodbackend.controllers.base.BaseAdminController;
import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.dto.ParameterResponseDTO;
import org.example.foodbackend.services.ParameterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParameterAdminAdminControllerImpl extends BaseAdminController<Parameter, Long, ParameterService> implements ParameterAdminController {
    public ParameterAdminAdminControllerImpl(ParameterService service) {
        super(service);
    }
}
