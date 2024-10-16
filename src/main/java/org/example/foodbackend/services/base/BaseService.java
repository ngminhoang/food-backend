package org.example.foodbackend.services.base;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseService<T, ID> {
    T save(T entity);
    T update(T entity);
    void deleteById(ID id);
    T findById(ID id);
    ResponseEntity<List<T>> findAll();
    List<T> saveAll(List<T> entities);
}