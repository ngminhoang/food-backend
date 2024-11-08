package org.example.foodbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.enums.ELanguage;

import java.time.LocalDateTime;
import java.util.Set;

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
    @Column(length = 500)
    private String dish_img_url;
    @Column
    private int duration;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account user;
    @OneToMany(mappedBy = "post")
    private Set<InstructionStep> steps;
    @ManyToMany
    @JoinTable(name = "post_tools",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private Set<KitchenTool> tools;
    @ManyToMany
    @JoinTable(name = "post_spices",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "spice_id"))
    private Set<KitchenSpice> spices;
    @ManyToMany
    @JoinTable(name = "post_ingredients",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<KitchenIngredient> ingredients;
    @OneToMany(mappedBy = "post")
    private Set<CookHistory> histories;
    @ManyToMany
    @JoinTable(
            name = "post_sessions",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id")
    )
    private Set<DaySession> daySessions;
}
