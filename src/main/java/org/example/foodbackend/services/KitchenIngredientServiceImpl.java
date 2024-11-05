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
    public PaginatedResponseDTO<KitchenIngredient> getUserIngredients(Account user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserIngredient> userIngredients = userIngredientRepository.findByUser(user, pageable);
        Page<KitchenIngredient> ingredientPage = userIngredients.map(UserIngredient::getIngredient);
        return PaginatedResponseDTO.<KitchenIngredient>builder()
                .data(ingredientPage.getContent())
                .currentPage(ingredientPage.getNumber())
                .totalItems(ingredientPage.getTotalElements())
                .totalPages(ingredientPage.getTotalPages())
                .build();
    }

    @Override
    public ResponseEntity<KitchenIngredientRequestDTO>  addUserIngredient(Account user, KitchenIngredientRequestDTO kitchenIngredientRequestDTO) {
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
    public KitchenIngredientResponseDTO deleteUserIngredient(Account user, Long ingredientId) {
        return null;
    }
}
