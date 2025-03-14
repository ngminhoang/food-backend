package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.enums.ELanguage;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private Long id;
    private String mail;
    private String name;
    private String avatar_url;
    private ELanguage language;
    private int slots;
}
