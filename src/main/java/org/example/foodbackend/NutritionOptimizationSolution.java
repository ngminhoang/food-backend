package org.example.foodbackend;

import lombok.Getter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;

import java.util.List;

@PlanningSolution
public class NutritionOptimizationSolution {

    private List<Food> foodList;
    private double targetNutrition;
    private HardSoftScore score;

    public NutritionOptimizationSolution() {
        // Mặc định cho OptaPlanner sử dụng
    }

    public NutritionOptimizationSolution(List<Food> foodList, double targetNutrition) {
        this.foodList = foodList;
        this.targetNutrition = targetNutrition;
    }

    @PlanningEntityCollectionProperty
    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public double getTargetNutrition() {
        return targetNutrition;
    }

    public void setTargetNutrition(double targetNutrition) {
        this.targetNutrition = targetNutrition;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    @ValueRangeProvider(id = "foodRange")
    public List<Integer> getFoodQuantityRange() {
        // Ví dụ: lượng thực phẩm có thể nằm trong khoảng từ 0 đến 10
        return List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    public double calculateTotalPrice() {
        return foodList.stream().mapToDouble(food -> food.getPrice() * food.getQuantity()).sum();
    }
}