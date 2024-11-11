package org.example.foodbackend.controllers.base;

import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseAdminController<T, ID, S extends BaseService<T, ID>> {

    protected final S service;

    public BaseAdminController(S service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable ID id) {
        T entity = service.findById(id);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        T savedEntity = service.save(entity);
        return ResponseEntity.ok(savedEntity);
    }

    @PostMapping("/all")
    public ResponseEntity<List<T>> createAll(@RequestBody List<T> entities) {
        List<T> savedEnties = service.saveAll(entities);
        return ResponseEntity.ok(savedEnties);
    }

    @PutMapping
    public ResponseEntity<T> update(@RequestBody T entity) {
        T updatedEntity = service.update(entity);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
