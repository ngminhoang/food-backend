package org.example.foodbackend.services.scale_services;

import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;

import java.util.Arrays;
import java.util.List;

public class MealValueRangeProvider {

    // Define the value range for grams
    @ValueRangeProvider(id = "gramRange")
    public List<Double> provideGramRange() {
        return Arrays.asList(50.0, 100.0, 150.0, 200.0); // Adjust as necessary
    }

    @ValueRangeProvider(id = "calorieRange")
    public List<Double> provideCalorieRange() {
        return Arrays.asList(100.0, 200.0, 300.0, 400.0); // Adjust as necessary
    }

    @ValueRangeProvider(id = "proteinRange")
    public List<Double> provideProteinRange() {
        return Arrays.asList(10.0, 20.0, 30.0, 40.0); // Adjust as necessary
    }

    @ValueRangeProvider(id = "carbRange")
    public List<Double> provideCarbRange() {
        return Arrays.asList(30.0, 50.0, 70.0); // Adjust as necessary
    }

    @ValueRangeProvider(id = "fiberRange")
    public List<Double> provideFiberRange() {
        return Arrays.asList(5.0, 10.0, 15.0); // Adjust as necessary
    }

    @ValueRangeProvider(id = "fatRange")
    public List<Double> provideFatRange() {
        return Arrays.asList(10.0, 20.0, 30.0); // Adjust as necessary
    }

    @ValueRangeProvider(id = "satFatRange")
    public List<Double> provideSatFatRange() {
        return Arrays.asList(3.0, 5.0, 7.0); // Adjust as necessary
    }

    @ValueRangeProvider(id = "priceRange")
    public List<Integer> providePriceRange() {
        return Arrays.asList(100, 200, 300, 400); // Adjust as necessary
    }
}

