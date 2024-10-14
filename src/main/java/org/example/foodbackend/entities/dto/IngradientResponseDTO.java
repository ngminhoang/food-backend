package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.foodbackend.entities.Ingradient;

@Data
@AllArgsConstructor
public class IngradientResponseDTO {
    Long id;

    String name;

    Double nuGrams;
    Double nuCalories;
    Double nuProteins;
    Double nuCarbs;
    Double nuFibers;
    Double nuFats;
    Double nuSatFats;
    Integer nuPrice;

    public IngradientResponseDTO( Ingradient ingradient ) {
        this.id = ingradient.getId();
        this.name = ingradient.getName();
        this.nuGrams = ingradient.getNuGrams();
        this.nuCalories = ingradient.getNuCalories();
        this.nuProteins = ingradient.getNuProteins();
        this.nuCarbs = ingradient.getNuCarbs();
        this.nuFibers = ingradient.getNuFibers();
        this.nuFats = ingradient.getNuFats();
        this.nuSatFats = ingradient.getNuSatFats();
        this.nuPrice = ingradient.getNuPrice();
    }
}
