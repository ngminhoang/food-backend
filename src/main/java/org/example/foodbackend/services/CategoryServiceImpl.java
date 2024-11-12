package org.example.foodbackend.services;
import org.example.foodbackend.entities.Category;
import org.example.foodbackend.repositories.CategoryRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long, CategoryRepository> implements CategoryService {

    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
    }
}