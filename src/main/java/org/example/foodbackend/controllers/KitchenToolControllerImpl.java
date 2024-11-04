package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.repositories.AccountRepository;
import org.example.foodbackend.services.KitchenToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "FooFi - Kitchen Tool")
@RequestMapping("api/tool")
public class KitchenToolControllerImpl extends BaseController<KitchenTool, Long, KitchenToolService> {
    @Autowired
    public KitchenToolControllerImpl(KitchenToolService service) {
        super(service);
    }

    @GetMapping("/user/tool/list")
    public ResponseEntity<Set<KitchenTool>> getUserListTools(@AuthenticationPrincipal Account user) {
        return service.getUserKitchenTools(user);
    }

    @PostMapping("/user/tool")
    public ResponseEntity<List<KitchenTool>> addUserKitchenTool(@AuthenticationPrincipal Account user, @RequestBody List<Long> kitchenToolIds) {
        return service.addUserKitchenTool(user, kitchenToolIds);
    }
}
