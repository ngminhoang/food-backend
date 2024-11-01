package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.KitchenSpice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenSpiceRepository extends JpaRepository<KitchenSpice, Long> {
}
