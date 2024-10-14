package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.dto.IngradientResponseDTO;
import org.example.foodbackend.repositories.IngredientRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl extends BaseServiceImpl<Ingradient, Long, IngredientRepository> implements IngredientService {

    public IngredientServiceImpl(IngredientRepository repository) {
        super(repository);
    }
}
