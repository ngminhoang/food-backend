package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenIngredient;
import org.example.foodbackend.entities.dto.KitchenIngredientRequestDTO;
import org.example.foodbackend.entities.dto.KitchenIngredientResponseDTO;
import org.example.foodbackend.entities.dto.PaginatedResponseDTO;
import org.example.foodbackend.repositories.UserIngredientRepository;
import org.example.foodbackend.services.KitchenIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ingredient")
@Tag(name = "FooFi - Kitchen Ingredient")
public class KitchenIngredientControllerImpl extends BaseController<KitchenIngredient, Long, KitchenIngredientService> {
    public KitchenIngredientControllerImpl(KitchenIngredientService service, UserIngredientRepository userIngredientRepository) {
        super(service);
    }

    @GetMapping("/user/ingredient/list")
    public PaginatedResponseDTO<KitchenIngredientResponseDTO> getUserKitchenIngredients(@AuthenticationPrincipal Account account, @RequestParam int page, @RequestParam int size) {
        return service.getUserIngredients(account, page, size);
    }

    @PostMapping("user/ingredient")
    public ResponseEntity<KitchenIngredientRequestDTO> addUserKitchenIngredients(@AuthenticationPrincipal Account account, @RequestBody KitchenIngredientRequestDTO ingredient) {
        return service.addUserIngredient(account, ingredient);
    }

    @DeleteMapping("user/ingredient/{id}")
    public ResponseEntity<KitchenIngredientResponseDTO> deleteUserKitchenIngredients(@AuthenticationPrincipal Account account, @PathVariable Long id) {
        return service.deleteUserIngredient(account, id);
    }

    @GetMapping("user/ingredient/not-added")
    public PaginatedResponseDTO<KitchenIngredientResponseDTO> getListIngredientNotAdded(
            @AuthenticationPrincipal Account account,
            @RequestParam int page,
            @RequestParam int size) {
        return service.getListIngredientsNotAdded(account, page, size);
    }

    @PutMapping("user/ingredient")
    public ResponseEntity<KitchenIngredientResponseDTO> editQuantityIngredient(
            @AuthenticationPrincipal Account user,
            @RequestBody KitchenIngredientRequestDTO ingredient
    ) {
        return service.editQuantityIngredient(user, ingredient);
    }
}
