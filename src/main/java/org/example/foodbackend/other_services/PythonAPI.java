package org.example.foodbackend.other_services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.foodbackend.entities.IngradientPercent;
import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.Recipe;
import org.example.foodbackend.entities.dto.FlaskResponseDTO;
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

@Component
public class PythonAPI {

    HttpClient client = HttpClient.newHttpClient();

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public PythonAPI(){
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();

    }

    public Parameter calculateByBodyProperties(Double weight, Double height, Integer age, String gender, String activityLevel) {
        try {
            String apiUrl = String.format(APIConstants.API_URL_ROOT + APIConstants.CALCULATE_BY_BODY_API, weight, height, age, gender, activityLevel);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();
            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response JSON into a List of FlaskResponseDTO
            Parameter parameter= objectMapper.readValue(response.body(), Parameter.class);
            return parameter;
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Handle exceptions and return a default value
            return null;
        }
    }


    @Transactional
    public Parameter calculate(Double calories, Double proteins, Double carbs, Double fibers, Double fats, Double satFats) {

        try {
            // Build the API URL with parameters
            String apiUrl = String.format(APIConstants.API_URL_ROOT + APIConstants.CALCULATE_API, calories, proteins, fats, satFats, fibers, carbs);

            // Create an HTTP GET request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response JSON into a List of FlaskResponseDTO
            List<FlaskResponseDTO> apiResponse = objectMapper.readValue(response.body(), new TypeReference<List<FlaskResponseDTO>>() {});

            // Save the input parameters to the database
            Parameter parameter = Parameter.builder()
                    .sumCalories(calories)
                    .sumProteins(proteins)
                    .sumCarbs(carbs)
                    .sumFibers(fibers)
                    .sumFats(fats)
                    .sumSatFats(satFats)
                    .build();

            // Save the parameter first to get its ID
//            parameter = parameterRepository.save(parameter);

            List<Recipe> recipes = new ArrayList<>();

            // Process the list of FlaskResponseDTO
            for (FlaskResponseDTO dto : apiResponse) {
                IngradientPercent ingradientPercent = new IngradientPercent(dto.getPercent(), ingredientRepository.findById(dto.getId()).orElseThrow());

                // Create a new Recipe and associate it with the Parameter
                Recipe recipe = new Recipe();
                recipe.setIngradientPercents(List.of(ingradientPercent)); // If there's one-to-many or you can add multiple percentages here
                recipe.setParameter(parameter); // Associate the recipe with the saved Parameter
                recipes.add(recipe);
            }

            // Save the recipes (cascade should automatically save related IngredientPercent)
            recipes = recipeRepository.saveAll(recipes);

            // Attach the recipes to the parameter
            parameter.setRecipes(recipes);
            parameter = parameterRepository.save(parameter); // Save the parameter again if needed to persist the relationship

            // Return the saved parameter object
            return parameterRepository.findById(parameter.getId()).orElseThrow();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Handle exceptions and return a default value
            return Parameter.builder().sumFats(0.0).build();
        }
    }

}
