package org.example.foodbackend.services.scale_services;

import org.example.foodbackend.entities.Ingredient;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MealOptimizer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        NutritionLimits nutritionLimits = new NutritionLimits(50, 2000, 150, 30, 70, 20);



        SolverFactory<MealPlan> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(MealPlan.class)
                .withEntityClasses(Ingredient.class)
                .withConstraintProviderClass(NutritionConstraintProvider.class)
                .withTerminationSpentLimit(Duration.ofSeconds(60)));

        Solver<MealPlan> solver = solverFactory.buildSolver();

        MealPlan unsolvedSolution = new MealPlan(generateIngredients());

        MealPlan solvedSolution = solver.solve(unsolvedSolution);
        if (solvedSolution != null && solvedSolution.getIngredientList() != null) {
            List<Ingredient> optimizedIngredients = solvedSolution.getIngredientList();
            HardSoftScore finalScore = solvedSolution.getScore();

            // In ra tên và tỷ lệ của mỗi ingredient
            optimizedIngredients.forEach(ingredient -> {
                System.out.println("Ingredient: " + ingredient.getName() + ", Portion: " + ingredient.getPortion());
            });

            System.out.println("Total cost: " + finalScore.getSoftScore());
        } else {
            System.out.println("No valid solution found.");
        }

    }

    private static List<Ingredient> readIngredientsFromExcel() {
        return generateIngredients();
    }

    public static List<Ingredient> generateIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();

        ingredients.add(new Ingredient("Chicken Breast", 2000.0, 2000.0, 51.0, 151.0, 31.0, 71.0, 21.0, 1));
        ingredients.add(new Ingredient("Broccoli", 91.0, 55.0, 3.7, 11.2, 2.4, 0.6, 0.1, 4));
        ingredients.add(new Ingredient("Turkey", 100.0, 135.0, 30.0, 0.0, 0.0, 1.0, 0.3, 3));
        ingredients.add(new Ingredient("Salmon", 300.0, 400.0, 39.0, 0.0, 22.0, 5.0, 1.0, 8));
        ingredients.add(new Ingredient("Beef", 250.0, 250.0, 25.0, 0.0, 20.0, 9.0, 4.0, 10));
        ingredients.add(new Ingredient("Eggs", 100.0, 155.0, 13.0, 1.0, 0.0, 11.0, 3.0, 2));
        ingredients.add(new Ingredient("Brown Rice", 150.0, 165.0, 3.0, 35.0, 1.0, 1.0, 0.2, 1));
        ingredients.add(new Ingredient("Quinoa", 100.0, 120.0, 4.0, 21.0, 2.0, 2.0, 0.2, 2));
        ingredients.add(new Ingredient("Greek Yogurt", 200.0, 120.0, 20.0, 10.0, 0.0, 0.0, 0.0, 3));
        ingredients.add(new Ingredient("Almonds", 100.0, 575.0, 21.0, 20.0, 12.0, 50.0, 3.7, 5));
        ingredients.add(new Ingredient("Walnuts", 100.0, 654.0, 15.0, 14.0, 6.0, 65.0, 6.0, 6));
        ingredients.add(new Ingredient("Spinach", 100.0, 23.0, 2.9, 3.6, 2.2, 0.4, 0.1, 2));
        ingredients.add(new Ingredient("Carrots", 100.0, 41.0, 0.9, 10.0, 2.8, 0.2, 0.0, 1));
        ingredients.add(new Ingredient("Potatoes", 100.0, 77.0, 2.0, 17.0, 2.2, 0.1, 0.0, 1));
        ingredients.add(new Ingredient("Tomatoes", 100.0, 18.0, 0.9, 3.9, 1.2, 0.2, 0.0, 1));
        ingredients.add(new Ingredient("Onions", 100.0, 40.0, 1.1, 9.3, 1.7, 0.1, 0.0, 1));
        ingredients.add(new Ingredient("Garlic", 100.0, 149.0, 6.4, 33.1, 2.1, 0.5, 0.1, 2));
        ingredients.add(new Ingredient("Cucumber", 100.0, 16.0, 0.7, 3.6, 0.5, 0.1, 0.0, 1));
        ingredients.add(new Ingredient("Bell Pepper", 100.0, 31.0, 1.0, 6.0, 2.0, 0.3, 0.1, 1));
        ingredients.add(new Ingredient("Cauliflower", 100.0, 25.0, 1.9, 5.0, 2.0, 0.3, 0.1, 2));
        ingredients.add(new Ingredient("Zucchini", 100.0, 17.0, 1.2, 3.1, 1.1, 0.3, 0.1, 1));
        ingredients.add(new Ingredient("Sweet Potato", 100.0, 86.0, 1.6, 20.0, 3.0, 0.1, 0.0, 1));
        ingredients.add(new Ingredient("Peas", 100.0, 81.0, 5.4, 14.0, 5.0, 0.4, 0.1, 1));
        ingredients.add(new Ingredient("Chickpeas", 100.0, 164.0, 8.9, 27.4, 7.6, 2.6, 0.3, 3));
        ingredients.add(new Ingredient("Lentils", 100.0, 116.0, 9.0, 20.0, 8.0, 0.4, 0.1, 2));
        ingredients.add(new Ingredient("Tofu", 100.0, 76.0, 8.0, 2.0, 0.3, 4.8, 0.6, 2));
        ingredients.add(new Ingredient("Cheese", 100.0, 402.0, 25.0, 1.0, 0.0, 33.0, 21.0, 5));
        ingredients.add(new Ingredient("Butter", 100.0, 717.0, 0.9, 0.1, 0.0, 81.0, 51.0, 7));
        ingredients.add(new Ingredient("Olive Oil", 100.0, 884.0, 0.0, 0.0, 0.0, 100.0, 14.0, 9));
        ingredients.add(new Ingredient("Coconut Oil", 100.0, 862.0, 0.0, 0.0, 0.0, 100.0, 87.0, 8));
        ingredients.add(new Ingredient("Honey", 100.0, 304.0, 0.3, 82.4, 0.2, 0.0, 0.0, 3));
        ingredients.add(new Ingredient("Maple Syrup", 100.0, 261.0, 0.0, 67.0, 0.0, 0.0, 0.0, 4));
        ingredients.add(new Ingredient("Brown Sugar", 100.0, 387.0, 0.0, 100.0, 0.0, 0.0, 0.0, 2));
        ingredients.add(new Ingredient("White Rice", 100.0, 130.0, 2.4, 28.0, 0.4, 0.3, 0.1, 1));
        ingredients.add(new Ingredient("Pasta", 100.0, 371.0, 13.0, 75.0, 3.0, 1.5, 0.3, 2));
        ingredients.add(new Ingredient("Bread", 100.0, 265.0, 9.0, 49.0, 2.7, 3.2, 0.5, 1));
        ingredients.add(new Ingredient("Cereal", 100.0, 379.0, 7.0, 84.0, 3.2, 7.0, 1.5, 3));
        ingredients.add(new Ingredient("Granola", 100.0, 489.0, 10.0, 61.0, 8.0, 21.0, 3.0, 5));
        ingredients.add(new Ingredient("Peanut Butter", 100.0, 588.0, 25.0, 20.0, 6.0, 50.0, 10.0, 4));
        ingredients.add(new Ingredient("Nutella", 100.0, 539.0, 6.0, 57.0, 2.2, 30.0, 10.0, 5));
        ingredients.add(new Ingredient("Chocolate", 100.0, 546.0, 7.6, 61.0, 2.7, 31.0, 19.0, 4));
        ingredients.add(new Ingredient("Pineapple", 100.0, 50.0, 0.5, 13.0, 1.4, 0.1, 0.0, 2));
        ingredients.add(new Ingredient("Banana", 100.0, 89.0, 1.1, 23.0, 2.6, 0.3, 0.1, 1));
        ingredients.add(new Ingredient("Apple", 100.0, 52.0, 0.3, 14.0, 2.4, 0.2, 0.0, 1));
        ingredients.add(new Ingredient("Orange", 100.0, 47.0, 0.9, 12.0, 2.4, 0.1, 0.0, 1));
        ingredients.add(new Ingredient("Grapes", 100.0, 69.0, 0.7, 18.0, 0.9, 0.2, 0.1, 2));
        ingredients.add(new Ingredient("Mango", 100.0, 60.0, 0.8, 15.0, 1.6, 0.4, 0.1, 1));
        ingredients.add(new Ingredient("Strawberries", 100.0, 32.0, 0.7, 7.7, 2.0, 0.3, 0.0, 1));
        ingredients.add(new Ingredient("Blueberries", 100.0, 57.0, 0.7, 14.0, 2.4, 0.3, 0.1, 2));
        ingredients.add(new Ingredient("Raspberries", 100.0, 52.0, 1.2, 12.0, 6.5, 0.7, 0.1, 2));
        ingredients.add(new Ingredient("Blackberries", 100.0, 43.0, 1.4, 10.0, 5.3, 0.5, 0.1, 2));
        ingredients.add(new Ingredient("Cabbage", 100.0, 25.0, 1.3, 5.8, 2.5, 0.1, 0.0, 1));
        ingredients.add(new Ingredient("Celery", 100.0, 16.0, 0.7, 3.0, 1.6, 0.2, 0.0, 1));
        ingredients.add(new Ingredient("Pumpkin", 100.0, 26.0, 1.0, 7.0, 0.5, 0.1, 0.0, 1));
        ingredients.add(new Ingredient("Asparagus", 100.0, 20.0, 2.2, 3.7, 2.1, 0.2, 0.0, 2));
        ingredients.add(new Ingredient("Artichoke", 100.0, 47.0, 3.5, 10.5, 5.4, 0.2, 0.0, 2));
        ingredients.add(new Ingredient("Beets", 100.0, 43.0, 1.6, 9.6, 2.3, 0.2, 0.0, 1));
        ingredients.add(new Ingredient("Radishes", 100.0, 16.0, 0.7, 3.4, 1.6, 0.1, 0.0, 1));
        ingredients.add(new Ingredient("Chili Pepper", 100.0, 40.0, 1.9, 9.0, 1.5, 0.4, 0.1, 2));
        ingredients.add(new Ingredient("Sauerkraut", 100.0, 19.0, 1.3, 4.0, 1.9, 0.1, 0.0, 2));
        ingredients.add(new Ingredient("Pickles", 100.0, 11.0, 0.6, 2.4, 0.6, 0.2, 0.0, 1));
        ingredients.add(new Ingredient("Soy Sauce", 100.0, 53.0, 8.0, 0.0, 0.0, 0.0, 0.0, 1));
        ingredients.add(new Ingredient("Ketchup", 100.0, 100.0, 1.0, 24.0, 0.0, 0.1, 0.0, 2));
        ingredients.add(new Ingredient("Mustard", 100.0, 66.0, 5.0, 5.0, 0.0, 4.0, 0.5, 1));
        ingredients.add(new Ingredient("Mayonnaise", 100.0, 680.0, 0.5, 0.6, 0.0, 75.0, 11.0, 3));
        ingredients.add(new Ingredient("Vinegar", 100.0, 21.0, 0.0, 0.1, 0.0, 0.0, 0.0, 1));
        ingredients.add(new Ingredient("Cilantro", 100.0, 23.0, 2.1, 4.0, 1.5, 0.5, 0.1, 1));
        ingredients.add(new Ingredient("Basil", 100.0, 23.0, 3.2, 2.7, 1.0, 0.6, 0.1, 1));
        ingredients.add(new Ingredient("Parsley", 100.0, 36.0, 3.0, 6.3, 3.0, 0.8, 0.1, 1));
        ingredients.add(new Ingredient("Dill", 100.0, 43.0, 3.5, 7.0, 2.0, 1.0, 0.2, 1));
        ingredients.add(new Ingredient("Chives", 100.0, 30.0, 3.3, 4.3, 1.0, 0.3, 0.0, 1));
        ingredients.add(new Ingredient("Sage", 100.0, 315.0, 10.0, 60.0, 40.0, 5.0, 1.0, 3));
        ingredients.add(new Ingredient("Thyme", 100.0, 101.0, 5.0, 24.0, 10.0, 1.0, 0.1, 2));

        return ingredients;
    }
}
