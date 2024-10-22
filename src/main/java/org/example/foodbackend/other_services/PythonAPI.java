package org.example.foodbackend.other_services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.foodbackend.entities.Ingradient;
import org.example.foodbackend.entities.IngradientPercent;
import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.Recipe;
import org.example.foodbackend.entities.dto.FlaskResponseDTO;
import org.example.foodbackend.repositories.IngradientPercentRepository;
import org.example.foodbackend.repositories.IngredientRepository;
import org.example.foodbackend.repositories.ParameterRepository;
import org.example.foodbackend.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PythonAPI {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;
//
//    @Transactional
//    public void addIngredientPercentToIngredient(Long ingredientId, FlaskResponseDTO responseDTO) {
//        // Step 1: Retrieve the ingredient by ID
//        Ingradient ingredient = ingredientRepository.findById(ingredientId)
//                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
//
//        // Step 2: Create a new IngradientPercent instance
//        IngradientPercent ingredientPercent = new IngradientPercent();
//        ingredientPercent.setPercent(responseDTO.getPercent());
//
//        // Step 3: Add the ingredient to the IngradientPercent's ingredients list
//        ingredientPercent.setIngredients(List.of(ingredient));
//
//        // Step 4: Add the IngradientPercent to the ingredient's ingredientPercents list
//        ingredient.getIngredientPercents().add(ingredientPercent);
//
//        // Step 5: Save both the IngradientPercent and the Ingradient
//        ingredientPercentRepository.save(ingredientPercent);
//        ingredientRepository.save(ingredient);
//    }
    // Base URL of your external Python API
    private static final String API_URL = "http://localhost:5000/api/data?calo=%f&protein=%f&fat=%f&sat_fat=%f&fiber=%f&carb=%f";

    // Method to call external API with individual parameters
    public Parameter calculate(Double calories, Double proteins, Double carbs, Double fibers, Double fats, Double satFats) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Build the API URL with parameters
            String apiUrl = String.format(API_URL, calories, proteins, fats, satFats, fibers, carbs);

            // Create an HTTP GET request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response JSON into a List of FlaskResponseDTO
            List<FlaskResponseDTO> apiResponser = objectMapper.readValue(response.body(), new TypeReference<List<FlaskResponseDTO>>() {
            });


            // Save the input parameters and the associated recipes to the database
            Parameter parameter = parameterRepository.save(Parameter.builder()
                    .sumCalories(calories)
                    .sumProteins(proteins)
                    .sumCarbs(carbs)
                    .sumFibers(fibers)
                    .sumFats(fats)
                    .sumSatFats(satFats)
                    .build());


            List<Recipe> recipes = new ArrayList<>();

            // Process the list of FlaskResponseDTO
            List<IngradientPercent> ingradientPercents = new ArrayList<>();
            for (FlaskResponseDTO dto : apiResponser) {
                IngradientPercent ingradientPercent = new IngradientPercent(dto.getPercent(), ingredientRepository.findById(dto.getId()).get());
                ingradientPercents.add(ingradientPercent);
            }

            Recipe recipe = new Recipe();
            recipe.setIngradientPercents(ingradientPercents);
            recipe.setParameter(parameter);
            recipes.add(recipe);

            recipes = recipeRepository.saveAll(recipes);

            return parameterRepository.findById(parameter.getId()).get();

            // Return the saved parameter object (modify this as per your business logic)


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Handle exceptions and return a default value
            return Parameter.builder().sumFats(0.0).build();
        }
    }
}
