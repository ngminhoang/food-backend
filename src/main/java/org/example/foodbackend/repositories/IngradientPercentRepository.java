package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.IngradientPercent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngradientPercentRepository extends JpaRepository<IngradientPercent, Long> {
}
