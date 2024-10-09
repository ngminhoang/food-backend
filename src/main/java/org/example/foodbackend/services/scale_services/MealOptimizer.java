package org.example.foodbackend.services.scale_services;

import org.example.foodbackend.entities.Ingredient;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MealOptimizer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        NutritionLimits nutritionLimits = new NutritionLimits(50, 2000, 150, 30, 70, 20);



        // Create SolverConfig programmatically
        SolverConfig solverConfig = new SolverConfig()
                .withSolutionClass(MealPlan.class)
                .withEntityClasses(Ingredient.class)
                .withConstraintProviderClass(NutritionConstraintProvider.class);

        // Create a SolverFactory
        SolverFactory<MealPlan> solverFactory = SolverFactory.create(solverConfig);

        // Create a SolverManager
        SolverManager<MealPlan, Long> solverManager = SolverManager.create(solverFactory);

        // Set up the initial MealPlan with IngredientList
        MealPlan problem = new MealPlan();
        problem.setIngredientList(readIngredientsFromExcel()); // Function to read data from Excel

        // Solve the problem and retrieve the solution
        MealPlan solution = solverManager.solve(1L, problem).getFinalBestSolution();

        // Display the optimized result
        System.out.println("Optimized food items:");
        solution.getIngredientList().forEach(ingredient ->
                System.out.println(ingredient.getName() + " - " + ingredient.getNuPrice()));
    }

    private static List<Ingredient> readIngredientsFromExcel() {
        return generateIngredients();
    }

    public static List<Ingredient> generateIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();

        ingredients.add(new Ingredient("Chicken Breast", 100.0, 165.0, 31.0, 0.0, 0.0, 3.6, 1.0, 2));
        ingredients.add(new Ingredient("Broccoli", 91.0, 55.0, 3.7, 11.2, 2.4, 0.6, 0.1, 1));
        // ... Add more ingredients as needed
        ingredients.add(new Ingredient("Turkey", 100.0, 135.0, 30.0, 0.0, 0.0, 1.0, 0.3, 3));

        return ingredients;
    }
}
