package org.example.foodbackend.services;

import org.example.foodbackend.entities.KitchenIngredient;
import org.example.foodbackend.repositories.KitchenIngredientRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class KitchenIngredientServiceImpl extends BaseServiceImpl<KitchenIngredient, Long, KitchenIngredientRepository> implements KitchenIngredientService {
    public KitchenIngredientServiceImpl(KitchenIngredientRepository rootRepository) {
        super(rootRepository);
    }
}
