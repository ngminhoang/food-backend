package org.example.foodbackend.controllers.admin;

import org.example.foodbackend.controllers.base.BaseAdminController;
import org.example.foodbackend.controllers.mapper.ParameterMapper;
import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.dto.ParameterRequestDTO;
import org.example.foodbackend.entities.dto.ParameterResponseDTO;
import org.example.foodbackend.services.ParameterService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParameterAdminControllerImpl extends BaseAdminController<Parameter, Long, ParameterService, ParameterRequestDTO, ParameterResponseDTO> implements ParameterAdminController {
    public ParameterAdminControllerImpl(ParameterService service, ParameterMapper mapper) {
        super(service,mapper);
    }
}
