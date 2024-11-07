package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenIngredient;
import org.example.foodbackend.entities.UserIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {
    Page<UserIngredient> findByUser(Account user, Pageable pageable);

    @Query("SELECT ui FROM UserIngredient ui WHERE ui.user.id = :userId AND ui.ingredient.id = :ingredientId")
    Optional<UserIngredient> findByUserIdAndIngredientId(Long userId, Long ingredientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserIngredient ui WHERE ui.user.id = :userId AND ui.ingredient.id = :ingredientId")
    void deleteByIngredientIdAndUserId(Long ingredientId, Long userId);
}
