package org.example.foodbackend.services.scale_services;

import org.example.foodbackend.entities.Ingredient;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

public class MealOptimizer {

    public static void main(String[] args) {
        // Create a SolverFactory
        SolverFactory<MealPlan> solverFactory = SolverFactory.create(new NutritionConstraintProvider());

        // Create a SolverManager
        SolverManager<MealPlan, Long> solverManager = SolverManager.create(solverFactory);

        // Set up the initial MealPlan with IngredientList
        MealPlan problem = new MealPlan();
        problem.setIngredientList(readIngredientsFromExcel()); // Function to read data from Excel

        MealPlan solution = (MealPlan) solverManager.solve(1L, problem);

        // Display the optimized result
        System.out.println("Optimized food items:");
        solution.getIngredientList().forEach(ingredient -> System.out.println(ingredient.getName() + " - " + ingredient.getNuPrice()));
    }

    private static List<Ingredient> readIngredientsFromExcel() {
        return null;
    }
}
