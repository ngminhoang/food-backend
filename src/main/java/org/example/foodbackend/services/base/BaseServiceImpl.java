package org.example.foodbackend.services.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T, ID, R extends JpaRepository<T, ID>>{

    protected R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public T update(T entity) {
        return repository.save(entity);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public T findById(ID id) {
        Optional<T> result = repository.findById(id);
        return result.orElse(null);
    }

    public ResponseEntity<List<T>> findAll() {
        try{
            return ResponseEntity.ok(repository.findAll());
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }
}
