package org.example.foodbackend.services.scale_services;

import org.example.foodbackend.entities.Ingredient;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.ConstraintStream;
import org.optaplanner.core.config.solver.SolverConfig;

public class NutritionConstraintProvider extends SolverConfig implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                proteinConstraint(constraintFactory),
                carbsConstraint(constraintFactory),
                fatConstraint(constraintFactory),
                minimizeCostConstraint(constraintFactory)
        };
    }

    private Constraint proteinConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .groupBy(Ingredient::getNuProteins)
                .filter(totalProtein -> totalProtein < 50)
                .penalize("Protein requirement", HardSoftScore.ONE_HARD);
    }







    private Constraint carbsConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .groupBy(Ingredient::getNuCarbs)
                .filter(totalCarbs -> totalCarbs < 150)
                .penalize("Carb requirement", HardSoftScore.ONE_HARD);
    }

    private Constraint fatConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .groupBy(Ingredient::getNuFats)
                .filter(totalFat -> totalFat < 20)
                .penalize("Fat requirement", HardSoftScore.ONE_HARD);
    }

    private Constraint minimizeCostConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Ingredient.class)
                .penalize("Minimize cost", HardSoftScore.ONE_SOFT, Ingredient::getNuPrice);
    }
}

