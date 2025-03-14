package org.example.foodbackend.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToolCheckDTO  {
    @JsonProperty("is_available")
    private boolean isAvailable;
    private Long id;
    private String name_en;
    private String name_vi;
    private String img_url;
}
