package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.entities.enums.SearchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findBySearchStatus(SearchStatus searchStatus, Pageable pageable);

    List<Ingredient> findByIdIn(List<Long> ids);

    @Query("SELECT i FROM Ingredient i WHERE SIZE(i.imgPaths) = 0")
    List<Ingredient> findAllWhereNoImg(Pageable pageable);

    @Query("SELECT i FROM Ingredient i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%')) AND i.isVerified = :isVerified")
    Page<Ingredient> findIngredientsBySearchAndIsVerified(@Param("search") String search,
                                                          @Param("isVerified") Boolean isVerified,
                                                          Pageable pageable);

    @Query("SELECT i FROM Ingredient i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Ingredient> findIngredientsBySearch(@Param("search") String search, Pageable pageable);


    @Query("""
                SELECT i 
                FROM Ingredient i
                LEFT JOIN IngradientPercent ip ON i.id = ip.ingredient.id
                GROUP BY i
                ORDER BY COUNT(ip.id) ASC
            """)
    Page<Ingredient> findIngredientsOrderedByCountAsc(Pageable pageable);

    @Query("""
                SELECT i 
                FROM Ingredient i
                LEFT JOIN IngradientPercent ip ON i.id = ip.ingredient.id
                GROUP BY i
                ORDER BY COUNT(ip.id) DESC
            """)
    Page<Ingredient> findIngredientsOrderedByCountDesc(Pageable pageable);


    @Transactional
    @Modifying
    @Query("UPDATE Ingredient i SET i.searchStatus = :status")
    void changeAllVerified(@Param("status") SearchStatus status);
}
