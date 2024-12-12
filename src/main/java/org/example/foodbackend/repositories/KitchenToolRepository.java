package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenToolRepository extends JpaRepository<KitchenTool, Long> {
    @Query("SELECT t FROM KitchenTool t WHERE NOT EXISTS " +
            "(SELECT 1 FROM t.listUsers u WHERE u = :account)")
    List<KitchenTool> getKitchenToolsNotAdded(Account account);
}
