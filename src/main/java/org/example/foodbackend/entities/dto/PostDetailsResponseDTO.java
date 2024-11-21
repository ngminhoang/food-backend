package org.example.foodbackend.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.KitchenIngredient;
import org.example.foodbackend.entities.KitchenSpice;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.entities.enums.ELanguage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailsResponseDTO {
    private Long id;
    private String dish_name;
    private String dish_img_url;
    private String description;
    private Integer duration;
    private ELanguage language;
    private LocalDateTime published_time;
    private UserInfoDTO user;
    private List<ToolCheckDTO> tools;
    private List<IngredientCheckDTO> ingredients;
    private List<SpiceCheckDTO> spices;
    private int likes;
    @JsonProperty("is_liked")
    private boolean is_liked;
    @JsonProperty
    private boolean is_standard;
}
