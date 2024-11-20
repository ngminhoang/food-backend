package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Category;
import org.example.foodbackend.entities.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT i FROM Category i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Category> findCategoriesBySearch(@Param("search") String search, Pageable pageable);

}
