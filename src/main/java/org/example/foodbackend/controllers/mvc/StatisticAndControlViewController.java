package org.example.foodbackend.controllers.mvc;

import org.example.foodbackend.services.IngredientService;
import org.example.foodbackend.services.MeiliSearchService;
import org.example.foodbackend.services.ParameterService;
import org.example.foodbackend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StatisticAndControlViewController {

    @Autowired
    ParameterService parameterService;
    @Autowired
    RecipeService recipeService;
    @Autowired
    MeiliSearchService meiliSearchService;
    @Autowired
    IngredientService ingredientService;

    @GetMapping("/quick-tools")
    public String quickTool(Model model) {
        Integer countOfParameter = parameterService.getCount();
        Integer countOfRecipe = recipeService.getCount();
        Integer countOfIngredient = ingredientService.getCount();
        Integer countOfSearchedIngredient = meiliSearchService.getCount();

        model.addAttribute("countOfParameters", countOfParameter);
        model.addAttribute("countOfRecipes", countOfRecipe);
        model.addAttribute("countOfIngredient", countOfIngredient);
        model.addAttribute("countOfSearchedIngredient", countOfIngredient);
        return "quick-tools";
    }

    @PostMapping("/re-update-search")
    public String reUpdateSearch() {
        meiliSearchService.clearAllIngredientInSeach();
        return "redirect:/quick-tools";
    }

}
