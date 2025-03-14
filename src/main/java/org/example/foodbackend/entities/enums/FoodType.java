package org.example.foodbackend.entities.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum FoodType {
    MAIN(1),
    SECOND(2),
    SNACK(3),
    SPICE(4);
    private final int value;

    FoodType(int value) {
        this.value = value;
    }

    public static FoodType fromValue(int value) {
        for (FoodType level : FoodType.values()) {
            if (level.getValue() == value) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid level value: " + value);
    }

    public static List<String> AllValue() {
        return Arrays.stream(FoodType.values())
                .map(FoodType::name)
                .toList();
    }
}
