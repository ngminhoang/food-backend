package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Ingradient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingradient, Long> {
}
