package org.example.foodbackend.services;

import lombok.extern.slf4j.Slf4j;
import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.entities.dto.MeilisearchIngredient;
import org.example.foodbackend.entities.enums.SearchStatus;
import org.example.foodbackend.repositories.IngredientRepository;
import org.example.foodbackend.repositories.MeilisearchRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IngredientServiceImpl extends BaseServiceImpl<Ingredient, Long, IngredientRepository> implements IngredientService {

    public IngredientServiceImpl(IngredientRepository repository) {
        super(repository);
    }

    @Autowired
    MeilisearchRepository meilisearchRepository;


    @Scheduled(fixedDelay = 5000L)
    private void synchronousIngredient() {

        PageRequest pageRequest = PageRequest.of(0, 50);

        // Get the page with the specified number of results
        List<Ingredient> ingredients = rootRepository.findBySearchStatus(SearchStatus.PENDING, pageRequest);

        log.error("pending ingredients: " + String.valueOf(ingredients.size()));

//        List<MeilisearchIngredient> meilisearchIngredients = ingradients.stream().map(MeilisearchIngredient::new).toList();
        for (Ingredient ingredient : ingredients) {
            try {
                ingredient.setSearchStatus(SearchStatus.SUCCESS);
                MeilisearchIngredient meilisearchIngredient = new MeilisearchIngredient(ingredient);
                meilisearchRepository.insertIngredient(meilisearchIngredient);
            } catch (Exception e) {
                ingredient.setSearchStatus(SearchStatus.FAIL);
            } finally {
                rootRepository.save(ingredient);
            }
        }
    }

    @Override
    public ResponseEntity<List<Ingredient>> search(String query, int page, int size, String sort, String order) {
        List<Long> ids = meilisearchRepository.searchIngredients(query, page, size, sort, order);
        return ResponseEntity.ok(rootRepository.findByIdIn(ids));

    }

    @Override
    public ResponseEntity<List<String>> suggestion(String query) {
        List<String> names = meilisearchRepository.suggestIngredients(query);
        return ResponseEntity.ok(names);
    }

    @Override
    public Page<Ingredient> findIngredients(String search, Boolean isVerified, int page, int size, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size);
        if(search==null){
            search = "";
        }

        if(sortOrder!=null && !sortOrder.isEmpty()){
            if(sortOrder.equals("asc")){
                return rootRepository.findIngredientsOrderedByCountAsc(pageable);
            }
            return rootRepository.findIngredientsOrderedByCountDesc(pageable);
        }

        if(isVerified==null){
            return rootRepository.findIngredientsBySearch(search, pageable);
        }
        else{
            return rootRepository.findIngredientsBySearchAndIsVerified(search, isVerified, pageable);
        }


    }

    @Override
    public void changeVerifiedStatus(Long id) {
        Ingredient ingredient = rootRepository.findById(id).get();
        ingredient.setIsVerified(!ingredient.getIsVerified());
        rootRepository.save(ingredient);
    }

    @Override
    public Integer getCount() {
        return Math.toIntExact(rootRepository.count());
    }
}
