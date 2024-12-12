package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KitchenDTO {
    private Long id;
    private String name_en;
    private String name_vi;
    private String img_url;
}
