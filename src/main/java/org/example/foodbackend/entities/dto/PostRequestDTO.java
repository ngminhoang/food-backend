package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.*;
import org.example.foodbackend.entities.enums.ELanguage;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    private String description;
    private ELanguage language;
    private String dish_name;
    private String dish_img_url;
    private Integer duration;
    private List<DaySession> sessions;
    private List<KitchenTool> tools;
    private List<KitchenSpice> spices;
    private List<KitchenIngredient> ingredients;
    private List<InstructionStep> steps;
}