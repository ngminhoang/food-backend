package org.example.foodbackend.services;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenIngredient;
import org.example.foodbackend.entities.UserIngredient;
import org.example.foodbackend.entities.dto.KitchenIngredientRequestDTO;
import org.example.foodbackend.entities.dto.KitchenIngredientResponseDTO;
import org.example.foodbackend.entities.dto.PaginatedResponseDTO;
import org.example.foodbackend.repositories.AccountRepository;
import org.example.foodbackend.repositories.KitchenIngredientRepository;
import org.example.foodbackend.repositories.UserIngredientRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class KitchenIngredientServiceImpl extends BaseServiceImpl<KitchenIngredient, Long, KitchenIngredientRepository> implements KitchenIngredientService {
    private final UserIngredientRepository userIngredientRepository;
    private final AccountRepository accountRepository;

    public KitchenIngredientServiceImpl(KitchenIngredientRepository rootRepository, UserIngredientRepository userIngredientRepository, AccountRepository accountRepository) {
        super(rootRepository);
        this.userIngredientRepository = userIngredientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public PaginatedResponseDTO<KitchenIngredientResponseDTO> getUserIngredients(Account user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserIngredient> userIngredients = userIngredientRepository.findByUser(user, pageable);
        Page<KitchenIngredientResponseDTO> ingredientPage = userIngredients.map(userIngredient -> {
            KitchenIngredient kitchenIngredient = userIngredient.getIngredient();
            return KitchenIngredientResponseDTO.builder()
                    .id(kitchenIngredient.getId())
                    .name_vi(kitchenIngredient.getName_vi())
                    .name_en(kitchenIngredient.getName_en())
                    .img_url(kitchenIngredient.getImg_url())
                    .unit(kitchenIngredient.getUnit())
                    .quantity(userIngredient.getQuantity())
                    .build();
        });
        return PaginatedResponseDTO.<KitchenIngredientResponseDTO>builder()
                .data(ingredientPage.getContent())
                .currentPage(ingredientPage.getNumber())
                .totalItems(ingredientPage.getTotalElements())
                .totalPages(ingredientPage.getTotalPages())
                .build();
    }

    @Override
    public ResponseEntity<KitchenIngredientRequestDTO> addUserIngredient(Account user, KitchenIngredientRequestDTO kitchenIngredientRequestDTO) {
        try {
            Account account = accountRepository.findById(user.getId()).get();
            KitchenIngredient kitchenIngredient = rootRepository.findById(kitchenIngredientRequestDTO.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
            UserIngredient userIngredient = UserIngredient.builder()
                    .user(account)
                    .ingredient(kitchenIngredient)
                    .quantity(kitchenIngredientRequestDTO.getQuantity())
                    .build();
            userIngredientRepository.save(userIngredient);
            return ResponseEntity.ok(kitchenIngredientRequestDTO);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<KitchenIngredientResponseDTO> deleteUserIngredient(Account user, Long ingredientId) {
        try {
            KitchenIngredient ingredient = rootRepository.findById(ingredientId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            UserIngredient userIngredient = userIngredientRepository.findByUserIdAndIngredientId(user.getId(), ingredient.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
            userIngredientRepository.deleteByIngredientIdAndUserId(ingredientId, user.getId());

            return ResponseEntity.ok(KitchenIngredientResponseDTO.builder()
                    .id(ingredient.getId())
                    .name_vi(ingredient.getName_vi())
                    .name_en(ingredient.getName_en())
                    .img_url(ingredient.getImg_url())
                    .unit(ingredient.getUnit())
                    .quantity(userIngredient.getQuantity())
                    .build());
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public PaginatedResponseDTO<KitchenIngredientResponseDTO> getListIngredientsNotAdded(Account user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KitchenIngredient> ingredientsPages = rootRepository.getAllKitchenIngredientsNotAdded(user, pageable);
        Page<KitchenIngredientResponseDTO> kitchenIngredientResponseDTOS = ingredientsPages.map(ingredientsPage -> KitchenIngredientResponseDTO.builder().id(ingredientsPage.getId())
                .name_vi(ingredientsPage.getName_vi())
                .name_en(ingredientsPage.getName_en())
                .img_url(ingredientsPage.getImg_url())
                .unit(ingredientsPage.getUnit())
                .build());
        return PaginatedResponseDTO.<KitchenIngredientResponseDTO>builder()
                .data(kitchenIngredientResponseDTOS.getContent())
                .currentPage(kitchenIngredientResponseDTOS.getNumber())
                .totalItems(kitchenIngredientResponseDTOS.getTotalElements())
                .totalPages(kitchenIngredientResponseDTOS.getTotalPages())
                .build();
    }
}
