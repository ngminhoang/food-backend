package org.example.foodbackend.entities.dto;

import lombok.Data;

import java.security.PublicKey;

@Data
public class ParameterRequestDTO {
    public Double sumCalories;
    public Double sumProteins;
    public Double sumCarbs;
    public Double sumFibers;
    public Double sumFats;
    public Double sumSatFats;
}
