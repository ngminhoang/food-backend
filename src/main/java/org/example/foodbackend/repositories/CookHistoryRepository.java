package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.CookHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookHistoryRepository extends JpaRepository<CookHistory, Long> {
    Page<CookHistory> findAllByUser(Account user, Pageable pageable);
}
