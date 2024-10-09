package org.example.foodbackend;

import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.config.solver.SolverConfig;

import java.util.Arrays;
import java.util.List;

public class NutritionOptimizer {
    public static void main(String[] args) {
        // Tạo cấu hình cho Solver
        SolverFactory<NutritionOptimizationSolution> solverFactory = SolverFactory.create(
                new SolverConfig()
                        .withSolutionClass(NutritionOptimizationSolution.class)
                        .withEntityClasses(Food.class)
                        .withEasyScoreCalculatorClass(NutritionScoreCalculator.class)
        );

        Solver<NutritionOptimizationSolution> solver = solverFactory.buildSolver();

        List<Food> foodList = Arrays.asList(
                new Food("Thực phẩm A", 10.0, 2), // 10.0 (giá) * 2 (số lượng) = 20.0
                new Food("Thực phẩm B", 15.0, 1), // 15.0 * 1 = 15.0
                new Food("Thực phẩm C", 20.0, 0), // 20.0 * 0 = 0.0
                new Food("Thực phẩm D", 25.0, 3)  // 25.0 * 3 = 75.0
        );

        double targetNutrition = 110.0; // Dinh dưỡng mục tiêu
        NutritionOptimizationSolution solution = new NutritionOptimizationSolution(foodList, targetNutrition);

        // Giải bài toán tối ưu hóa
        NutritionOptimizationSolution bestSolution = solver.solve(solution);

        // Kết quả
        System.out.println("Tổng giá tiền tối thiểu: " + bestSolution.calculateTotalPrice());
        for (Food food : bestSolution.getFoodList()) {
            if (food.getQuantity() > 0) {
                System.out.println(food.getName() + ": " + food.getQuantity());
            }
        }
    }
}
