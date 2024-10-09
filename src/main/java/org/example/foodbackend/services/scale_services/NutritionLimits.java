package org.example.foodbackend.services.scale_services;

public class NutritionLimits {
    private int maxProtein;
    private int maxCalories;
    private int maxCarbs;
    private int maxFibers;
    private int maxFats;
    private int maxSaturatedFats;

    // Constructor
    public NutritionLimits(int maxProtein, int maxCalories, int maxCarbs, int maxFibers, int maxFats, int maxSaturatedFats) {
        this.maxProtein = maxProtein;
        this.maxCalories = maxCalories;
        this.maxCarbs = maxCarbs;
        this.maxFibers = maxFibers;
        this.maxFats = maxFats;
        this.maxSaturatedFats = maxSaturatedFats;
    }

    // Getters
    public int getMaxProtein() {
        return maxProtein;
    }

    public int getMaxCalories() {
        return maxCalories;
    }

    public int getMaxCarbs() {
        return maxCarbs;
    }

    public int getMaxFibers() {
        return maxFibers;
    }

    public int getMaxFats() {
        return maxFats;
    }

    public int getMaxSaturatedFats() {
        return maxSaturatedFats;
    }
}
