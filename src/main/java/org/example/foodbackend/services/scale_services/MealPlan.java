package org.example.foodbackend.services.scale_services;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.foodbackend.entities.Ingredient;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.Arrays;
import java.util.List;

@Setter
@PlanningSolution
@NoArgsConstructor
public class MealPlan {

    private List<Ingredient> ingredientList;
    private HardSoftScore score;

    public MealPlan(List<Ingredient> ingredients) {
        this.ingredientList = ingredients;
    }

    // Danh sách nguyên liệu để tối ưu hóa
    @PlanningEntityCollectionProperty
    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    // Điểm tối ưu hóa
    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    // Định nghĩa các khoảng giá trị cho các biến lập kế hoạch trong lớp giải pháp
    @ValueRangeProvider(id = "calorieRange")
    public List<Double> provideCalorieRange() {
        return Arrays.asList(100.0, 200.0, 300.0, 400.0); // Điều chỉnh nếu cần
    }

    @ValueRangeProvider(id = "portionRange")
    public List<Double> providePortionRange() {
        return Arrays.asList(1.00,100.0, 200.0, 300.0, 400.0); // Điều chỉnh nếu cần
    }

    @ValueRangeProvider(id = "proteinRange")
    public List<Double> provideProteinRange() {
        return Arrays.asList(10.0, 20.0, 30.0, 40.0); // Điều chỉnh nếu cần
    }

    @ValueRangeProvider(id = "carbRange")
    public List<Double> provideCarbRange() {
        return Arrays.asList(30.0, 50.0, 70.0); // Điều chỉnh nếu cần
    }

    @ValueRangeProvider(id = "fiberRange")
    public List<Double> provideFiberRange() {
        return Arrays.asList(5.0, 10.0, 15.0); // Điều chỉnh nếu cần
    }

    @ValueRangeProvider(id = "fatRange")
    public List<Double> provideFatRange() {
        return Arrays.asList(10.0, 20.0, 30.0); // Điều chỉnh nếu cần
    }

    @ValueRangeProvider(id = "satFatRange")
    public List<Double> provideSatFatRange() {
        return Arrays.asList(3.0, 5.0, 7.0); // Điều chỉnh nếu cần
    }

    @ValueRangeProvider(id = "priceRange")
    public List<Integer> providePriceRange() {
        return Arrays.asList(100, 200, 300, 400); // Điều chỉnh nếu cần
    }
}
