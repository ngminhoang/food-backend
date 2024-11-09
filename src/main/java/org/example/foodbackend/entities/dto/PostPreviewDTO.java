package org.example.foodbackend.entities.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PostPreviewDTO {
    String dish_name;
    String dish_img_url;
    String description;
    Integer duration;
    int likes;
    boolean is_liked;
    UserInfoDTO user;
}
