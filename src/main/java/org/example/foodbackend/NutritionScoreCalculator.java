package org.example.foodbackend;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class NutritionScoreCalculator implements EasyScoreCalculator<NutritionOptimizationSolution, HardSoftScore> {

    @Override
    public HardSoftScore calculateScore(NutritionOptimizationSolution solution) {
        double totalNutrition = 0;
        double totalPrice = 0;

        // Tính tổng dinh dưỡng và tổng giá tiền
        for (Food food : solution.getFoodList()) {
            totalNutrition += food.getNutrition() * food.getQuantity();
            totalPrice += food.getPrice() * food.getQuantity();
        }

        // Hard constraint: Đảm bảo dinh dưỡng đạt yêu cầu
        int nutritionPenalty = (int) Math.max(0, solution.getTargetNutrition() - totalNutrition);

        // Soft constraint: Tối ưu hóa giá tiền
        int totalPriceScore = (int) -totalPrice;

        // HardSoftScore với hard score cho vi phạm về dinh dưỡng, soft score tối ưu giá tiền
        return HardSoftScore.of(-nutritionPenalty, totalPriceScore);
    }
}
