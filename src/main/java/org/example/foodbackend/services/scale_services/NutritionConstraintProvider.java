package org.example.foodbackend.services.scale_services;

import org.example.foodbackend.entities.Ingredient;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.config.solver.SolverConfig;

public class NutritionConstraintProvider extends SolverConfig implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                proteinConstraint(constraintFactory),
                caloriesConstraint(constraintFactory),
                carbsConstraint(constraintFactory),
                fibersConstraint(constraintFactory),
                fatsConstraint(constraintFactory),
                saturatedFatsConstraint(constraintFactory),
                minimizeCostConstraint(constraintFactory)
        };
    }

    // Ràng buộc cho protein
    private Constraint proteinConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .groupBy(ConstraintCollectors.sum(ingredient -> ingredient.getNuProteins().intValue()))
                .filter(totalProtein -> totalProtein < 50)
                .penalize("Protein requirement", HardSoftScore.ONE_HARD);
    }

    // Ràng buộc cho calories
    private Constraint caloriesConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .groupBy(ConstraintCollectors.sum(ingredient -> ingredient.getNuCalories().intValue()))
                .filter(totalCalo -> totalCalo < 2000)
                .penalize("Calories requirement", HardSoftScore.ONE_HARD);
    }

    // Ràng buộc cho carbs
    private Constraint carbsConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .groupBy(ConstraintCollectors.sum(ingredient -> ingredient.getNuCarbs().intValue()))
                .filter(totalCarbs -> totalCarbs < 150)
                .penalize("Carb requirement", HardSoftScore.ONE_HARD);
    }

    // Ràng buộc cho chất xơ (fibers)
    private Constraint fibersConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .groupBy(ConstraintCollectors.sum(ingredient -> ingredient.getNuFibers().intValue()))
                .filter(totalFibers -> totalFibers < 30)
                .penalize("Fiber requirement", HardSoftScore.ONE_HARD);
    }

    // Ràng buộc cho chất béo (fat)
    private Constraint fatsConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .groupBy(ConstraintCollectors.sum(ingredient -> ingredient.getNuFats().intValue()))
                .filter(totalFats -> totalFats < 70)
                .penalize("Fat requirement", HardSoftScore.ONE_HARD);
    }

    // Ràng buộc cho chất béo bão hòa (saturated fats)
    private Constraint saturatedFatsConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .groupBy(ConstraintCollectors.sum(ingredient -> ingredient.getNuSatFats().intValue()))
                .filter(totalSatFats -> totalSatFats < 20)
                .penalize("Saturated Fat requirement", HardSoftScore.ONE_HARD);
    }

    // Ràng buộc để tối thiểu hóa chi phí
    private Constraint minimizeCostConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .penalize("Minimize cost", HardSoftScore.ONE_SOFT, ingredient -> ingredient.getNuPrice().intValue());
    }
}
