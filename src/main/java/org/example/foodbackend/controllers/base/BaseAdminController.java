package org.example.foodbackend.controllers.base;

import org.example.foodbackend.controllers.mapper.Mapper;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseAdminController<T, ID, S extends BaseService<T, ID>, RQ, RS> {

    protected final S service;
    protected final Mapper<T, RQ, RS> mapper;

    public BaseAdminController(S service, Mapper<T, RQ, RS> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RS> findById(@PathVariable ID id) {
        T entity = service.findById(id);
        if (entity != null) {
            return ResponseEntity.ok(mapper.toResponse(entity));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<RS>> findAll() {
        List<RS> responses = service.findAll().getBody()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<RS> create(@RequestBody RQ requestDto) {
        T entity = mapper.toEntity(requestDto);
        T savedEntity = service.save(entity);
        return ResponseEntity.ok(mapper.toResponse(savedEntity));
    }

    @PostMapping("/all")
    public ResponseEntity<List<RS>> createAll(@RequestBody List<RQ> requestDtos) {
        List<T> entities = requestDtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        List<T> savedEntities = service.saveAll(entities);
        List<RS> responses = savedEntities.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping
    public ResponseEntity<RS> update(@RequestBody RQ requestDto) {
        T entity = mapper.toEntity(requestDto);
        T updatedEntity = service.update(entity);
        return ResponseEntity.ok(mapper.toResponse(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
