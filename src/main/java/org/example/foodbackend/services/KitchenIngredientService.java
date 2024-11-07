package org.example.foodbackend.services;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenIngredient;
import org.example.foodbackend.entities.dto.KitchenIngredientRequestDTO;
import org.example.foodbackend.entities.dto.KitchenIngredientResponseDTO;
import org.example.foodbackend.entities.dto.PaginatedResponseDTO;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

public interface KitchenIngredientService extends BaseService<KitchenIngredient, Long> {
    PaginatedResponseDTO<KitchenIngredientResponseDTO> getUserIngredients(Account user, int page, int size);
    ResponseEntity<KitchenIngredientRequestDTO> addUserIngredient(Account user, KitchenIngredientRequestDTO kitchenIngredientRequestDTO);
    ResponseEntity<KitchenIngredientResponseDTO> deleteUserIngredient(Account user, Long ingredientId);
    PaginatedResponseDTO<KitchenIngredientResponseDTO> getListIngredientsNotAdded(Account user, int page, int size);
    ResponseEntity<KitchenIngredientResponseDTO> editQuantityIngredient(Account user, KitchenIngredientRequestDTO kitchenIngredientRequestDTO);
}
