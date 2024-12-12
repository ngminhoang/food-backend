package org.example.foodbackend.controllers.base;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.dto.PaginatedResponseDTO;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<T, ID, S extends BaseService<T, ID>> {

    protected final S service;

    public BaseController(S service) {
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

    @GetMapping("/list")
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

    @GetMapping("/paginated-list")
    public ResponseEntity<PaginatedResponseDTO<T>> getListPaged(@RequestParam int page, @RequestParam int size) {
        try {
            Page<T> pageResult = service.getListPagination(page, size);
            List<T> dataList = pageResult.getContent();
            PaginatedResponseDTO<T> responseDTO = PaginatedResponseDTO.<T>builder()
                    .data(dataList)
                    .currentPage(pageResult.getNumber())
                    .totalPages(pageResult.getTotalPages())
                    .totalItems(pageResult.getTotalElements())
                    .build();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception error) {
            return ResponseEntity.noContent().build();
        }

    }
}
