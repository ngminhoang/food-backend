package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.enums.ELanguage;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post")
public class DishPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String description;
    @Column
    private LocalDateTime published_time;
    @Column
    private boolean is_standard;
    @Column
    private int likes;
    @Column
    private ELanguage language;
    @Column
    private String dish_name;
    @Column
    private String dish_img_url;
    @Column
    private int dish_duration;
}
