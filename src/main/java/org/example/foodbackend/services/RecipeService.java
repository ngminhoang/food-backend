package org.example.foodbackend.services;

import org.example.foodbackend.entities.Recipe;
import org.example.foodbackend.services.base.BaseService;

public interface RecipeService extends BaseService<Recipe, Long> {
    Integer getCount();
}
