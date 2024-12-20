package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.enums.SearchStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingradient, Long> {
    List<Ingradient> findBySearchStatus(SearchStatus searchStatus, Pageable pageable);
    List<Ingradient> findByIdIn(List<Long> ids);
}
