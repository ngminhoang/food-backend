package org.example.foodbackend.controllers.mapper;

public interface Mapper<T, RQ, RS> {
    T toEntity(RQ requestDto);
    RS toResponse(T entity);
}

