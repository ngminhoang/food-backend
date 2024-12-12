package org.example.foodbackend.entities.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.foodbackend.entities.enums.FoodType;

@Converter(autoApply = true)
public class FoodTypeConverter implements AttributeConverter<FoodType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FoodType foodType) {
        if (foodType == null) {
            return null;
        }
        return foodType.getValue();
    }

    @Override
    public FoodType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return FoodType.fromValue(dbData);
    }
}