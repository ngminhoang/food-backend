package org.example.foodbackend.services;
import org.example.foodbackend.entities.Category;
import org.example.foodbackend.repositories.CategoryRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long, CategoryRepository> implements CategoryService {

    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Page<Category> findCategories(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if(search==null){
            search = "";
        }
        return rootRepository.findCategoriesBySearch(search,pageable);
    }
}