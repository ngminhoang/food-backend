package org.example.foodbackend.services;

import org.example.foodbackend.entities.InstructionStep;
import org.example.foodbackend.repositories.InstructionStepRepository;
import org.example.foodbackend.services.base.BaseService;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class InstructionStepService extends BaseServiceImpl<InstructionStep, Long, InstructionStepRepository> implements BaseService<InstructionStep, Long> {
    public InstructionStepService(InstructionStepRepository rootRepository) {
        super(rootRepository);
    }
}
