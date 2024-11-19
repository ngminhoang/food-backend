package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.entities.enums.SearchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findBySearchStatus(SearchStatus searchStatus, Pageable pageable);
    List<Ingredient> findByIdIn(List<Long> ids);

    @Query("SELECT i FROM Ingredient i WHERE SIZE(i.imgPaths) = 0")
    List<Ingredient> findAllWhereNoImg(Pageable pageable);

    @Query("SELECT i FROM Ingredient i")
    Page<Ingredient> findIngredients(@Param("search") String search,
                                     @Param("isVerified") Boolean isVerified,
                                     Pageable pageable);
}
