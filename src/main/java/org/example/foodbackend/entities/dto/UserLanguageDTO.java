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
public class UserLanguageDTO {
    private ELanguage language;
}
