package org.example.foodbackend.controllers.mapper;

import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.dto.ParameterRequestDTO;
import org.example.foodbackend.entities.dto.ParameterResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ParameterMapper implements Mapper<Parameter, ParameterRequestDTO, ParameterResponseDTO> {

    @Override
    public Parameter toEntity(ParameterRequestDTO requestDto) {
        return Parameter.builder()
                .sumCalories(requestDto.getSumCalories())
                .sumFats(requestDto.getSumFats())
                .sumCarbs(requestDto.getSumCarbs())
                .sumProteins(requestDto.getSumProteins())
                .sumCalories(requestDto.getSumCalories())
                .sumFats(requestDto.getSumFats())
                .sumCarbs(requestDto.getSumCarbs())
                .build();
    }

    @Override
    public ParameterResponseDTO toResponse(Parameter entity) {
        return new ParameterResponseDTO(entity);
    }
}
