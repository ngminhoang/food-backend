package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.DaySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaySessionRepository extends JpaRepository<DaySession, Long> {
}
