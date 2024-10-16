package org.example.foodbackend.other_services;

import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.repositories.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PythonAPI {

    @Autowired
    private ParameterRepository parameterRepository;  // Remove static

    // Remove static from the method
    public Parameter caculate(Double calories, Double proteins, Double carbs, Double fibers, Double fats, Double satFats) {
        // Save the parameter object using the repository
        parameterRepository.save(Parameter.builder()
                .sumCalories(calories)
                .sumProteins(proteins)
                .sumCarbs(carbs)
                .sumFibers(fibers)
                .sumFats(fats)
                .sumSatFats(satFats)
                .build());

        // Return a dummy parameter object for now (or the saved one if needed)
        return Parameter.builder().sumFats(123.0).build();
    }
}
