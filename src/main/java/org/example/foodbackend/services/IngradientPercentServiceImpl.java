package org.example.foodbackend.services;

import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.IngradientPercent;
import org.example.foodbackend.repositories.IngradientPercentRepository;
import org.example.foodbackend.repositories.IngredientRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class IngradientPercentServiceImpl  extends BaseServiceImpl<IngradientPercent, Long, IngradientPercentRepository> implements IngradientPercentService{
    public IngradientPercentServiceImpl(IngradientPercentRepository repository) {
        super(repository);
    }
}
