package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenIngredientRepository extends JpaRepository<KitchenIngredient, Long> {

    @Query("SELECT i FROM KitchenIngredient i WHERE i.id NOT IN " +
            "(SELECT ui.ingredient.id FROM UserIngredient ui WHERE ui.user = :account)" +
            "AND LOWER(i.name_vi) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<KitchenIngredient> getAllKitchenIngredientsNotAddedVi(Account account, Pageable pageable, String query);
    @Query("SELECT i FROM KitchenIngredient i WHERE i.id NOT IN " +
            "(SELECT ui.ingredient.id FROM UserIngredient ui WHERE ui.user = :account)" +
            "AND LOWER(i.name_en) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<KitchenIngredient> getAllKitchenIngredientsNotAddedEn(Account account, Pageable pageable, String query);
    @Query("SELECT i FROM KitchenIngredient  i WHERE LOWER(i.name_en) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<KitchenIngredient> findAllByNameEn(Pageable pageable, String query);
    @Query("SELECT i FROM KitchenIngredient  i WHERE LOWER(i.name_vi) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<KitchenIngredient> findAllByNameVi(Pageable pageable, String query);
}
