package org.example.foodbackend.services;

import org.example.foodbackend.repositories.MeilisearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeiliSearchService {

    @Autowired
    MeilisearchRepository meilisearchRepository;

    public void clearAllIngredientInSeach(){
        meilisearchRepository.clearAllSearchedIngredient();
    }

    public Integer getCount(){
        return meilisearchRepository.getCountSearchedIngredient();
    }
}
