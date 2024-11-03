package org.example.foodbackend.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T, ID, R extends JpaRepository<T, ID>>{

    protected R rootRepository;

    public BaseServiceImpl(R rootRepository) {
        this.rootRepository = rootRepository;
    }

    public T save(T entity) {
        return rootRepository.save(entity);
    }

    public T update(T entity) {
        return rootRepository.save(entity);
    }

    public void deleteById(ID id) {
        rootRepository.deleteById(id);
    }

    public T findById(ID id) {
        Optional<T> result = rootRepository.findById(id);
        return result.orElse(null);
    }

    public ResponseEntity<List<T>> findAll() {
        try{
            return ResponseEntity.ok(rootRepository.findAll());
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    public Page<T> getListPagination(int page, int size) {
            Pageable pageable = PageRequest.of(page, size);
            return rootRepository.findAll(pageable);
    }

    public List<T> saveAll(List<T> entities) {
        return rootRepository.saveAll(entities);
    }
}
