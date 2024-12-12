package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.DaySession;
import org.example.foodbackend.services.DaySessionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/day-session")
@Tag(name = "FooFi - Day Session")
public class DaySessionController extends BaseController<DaySession, Long, DaySessionService> {

    public DaySessionController(DaySessionService service) {
        super(service);
    }
}
