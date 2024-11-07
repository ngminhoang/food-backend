package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenSpice;
import org.example.foodbackend.services.KitchenSpiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "FooFi - Kitchen Spice")
@RequestMapping("api/spice")
public class KitchenSpiceControllerImpl extends BaseController<KitchenSpice, Long, KitchenSpiceService> {
    public KitchenSpiceControllerImpl(KitchenSpiceService service) {
        super(service);
    }

    @PostMapping("/user/spice")
    public ResponseEntity<List<KitchenSpice>> addUserSpices(
            @AuthenticationPrincipal Account account
            , @RequestBody List<Long> spiceIds) {
        return service.addUserSpices(account, spiceIds);
    }

    @GetMapping("/user/spice/list")
    public ResponseEntity<List<KitchenSpice>> getUserSpices(@AuthenticationPrincipal Account account) {
        return service.getUserSpices(account);
    }

    @GetMapping("/user/spice/not-added")
    public ResponseEntity<List<KitchenSpice>> getUserSpiceNotAdded(@AuthenticationPrincipal Account account) {
        return service.getListSpicesNotAdded(account);
    }

    @DeleteMapping("user/spice/{id}")
    public ResponseEntity<KitchenSpice> deleteUserSpice(@AuthenticationPrincipal Account account, @PathVariable Long id) {
        return service.deleteUserSpices(account, id);
    }
}
