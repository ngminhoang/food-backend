package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.InstructionStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionStepRepository extends JpaRepository<InstructionStep, Long> {
}
