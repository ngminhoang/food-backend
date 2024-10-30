package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.enums.ELanguage;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private boolean is_standard;
    @Column
    private String description;
    @Column
    private LocalDateTime published_time;
    @Column
    private int likes;
    @Column
    private ELanguage language;
    @Column
    private String dish_name;
    @Column
    private String dish_img_url;
    @Column
    private int duration;
}
