package org.example.foodbackend.services;

import org.example.foodbackend.entities.Recipe;
import org.example.foodbackend.repositories.RecipeRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl  extends BaseServiceImpl<Recipe, Long, RecipeRepository> implements RecipeService {
    public RecipeServiceImpl(RecipeRepository repository) {
        super(repository);
    }

    @Override
    public Integer getCount() {
        return Math.toIntExact(rootRepository.count());
    }
}
