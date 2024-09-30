package org.example.foodbackend.services.scale_services;

import org.example.foodbackend.entities.Ingredient;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@PlanningSolution
public class MealPlan {

    private List<Ingredient> IngredientList;

    private HardSoftScore score;

    @PlanningEntityCollectionProperty
    public List<Ingredient> getIngredientList() {
        return IngredientList;
    }

    public void setIngredientList(List<Ingredient> IngredientList) {
        this.IngredientList = IngredientList;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }
}

