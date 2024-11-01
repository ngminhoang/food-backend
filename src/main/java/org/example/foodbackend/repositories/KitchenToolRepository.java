package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.KitchenTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenToolRepository extends JpaRepository<KitchenTool, Long> {
}
