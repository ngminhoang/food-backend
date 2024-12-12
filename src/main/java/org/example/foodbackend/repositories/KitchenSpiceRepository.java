package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenSpice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenSpiceRepository extends JpaRepository<KitchenSpice, Long> {
    @Query("SELECT s FROM KitchenSpice s WHERE NOT EXISTS " +
    "(SELECT 1 FROM s.users u WHERE u = :user)")
    List<KitchenSpice> getListSpicesNotAdded(Account user);
}
