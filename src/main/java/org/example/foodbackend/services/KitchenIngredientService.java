package org.example.foodbackend.services;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenIngredient;
import org.example.foodbackend.entities.dto.KitchenIngredientRequestDTO;
import org.example.foodbackend.entities.dto.KitchenIngredientResponseDTO;
import org.example.foodbackend.entities.dto.PaginatedResponseDTO;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

public interface KitchenIngredientService extends BaseService<KitchenIngredient, Long> {
    PaginatedResponseDTO<KitchenIngredient> getUserIngredients(Account user, int page, int size);
    ResponseEntity<KitchenIngredientRequestDTO> addUserIngredient(Account user, KitchenIngredientRequestDTO kitchenIngredientRequestDTO);
    KitchenIngredientResponseDTO deleteUserIngredient(Account user, Long ingredientId);
}
