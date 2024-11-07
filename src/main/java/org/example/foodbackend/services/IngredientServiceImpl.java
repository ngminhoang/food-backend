package org.example.foodbackend.services;

import lombok.extern.slf4j.Slf4j;
import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.dto.MeilisearchIngredient;
import org.example.foodbackend.entities.enums.SearchStatus;
import org.example.foodbackend.repositories.IngredientRepository;
import org.example.foodbackend.repositories.MeilisearchRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IngredientServiceImpl extends BaseServiceImpl<Ingradient, Long, IngredientRepository> implements IngredientService {

    public IngredientServiceImpl(IngredientRepository repository) {
        super(repository);
    }

    @Autowired
    MeilisearchRepository meilisearchRepository;


    @Scheduled(fixedDelay = 5000L)
    private void synchronousIngredient() {

        PageRequest pageRequest = PageRequest.of(0, 50);

        // Get the page with the specified number of results
        List<Ingradient> ingradients = rootRepository.findBySearchStatus(SearchStatus.PENDING, pageRequest);

        log.error("pending ingredients: " + String.valueOf(ingradients.size()));

//        List<MeilisearchIngredient> meilisearchIngredients = ingradients.stream().map(MeilisearchIngredient::new).toList();
        for (Ingradient ingradient : ingradients) {
            try {
                ingradient.setSearchStatus(SearchStatus.SUCCESS);
                MeilisearchIngredient meilisearchIngredient = new MeilisearchIngredient(ingradient);
                meilisearchRepository.insertIngredient(meilisearchIngredient);
            } catch (Exception e) {
                ingradient.setSearchStatus(SearchStatus.FAIL);
            } finally {
                rootRepository.save(ingradient);
            }
        }
    }

    @Override
    public ResponseEntity<List<Ingradient>> search(String query, int page, int size,String sort, String order) {
        List<Long> ids = meilisearchRepository.searchIngredients(query, page, size, sort, order);
        return ResponseEntity.ok(rootRepository.findByIdIn(ids));

    }

    @Override
    public ResponseEntity<List<String>> suggestion(String query) {
        List<String> names = meilisearchRepository.suggestIngredients(query);
        return ResponseEntity.ok(names);
    }
}
