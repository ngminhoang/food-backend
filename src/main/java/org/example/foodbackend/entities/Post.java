package org.example.foodbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.enums.ELanguage;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
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
    @JsonProperty("is_standard")
    private boolean is_standard;
    @Column
    private String description;
    @Column
    @CreationTimestamp
    private LocalDateTime published_time;
    @Column
    private ELanguage language;
    @Column
    private String dish_name;
    @Column(length = 500)
    private String dish_img_url;
    @Column
    private int duration;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Account user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstructionStep> steps;
    @ManyToMany
    @JoinTable(name = "post_tools",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private List<KitchenTool> tools;
    @ManyToMany
    @JoinTable(name = "post_spices",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "spice_id"))
    private List<KitchenSpice> spices;
    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<PostIngredient> post_ingredients;
    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<CookHistory> histories;
    @ManyToMany
    @JoinTable(
            name = "post_sessions",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id")
    )
    private List<DaySession> daySessions;
    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<Account> likedUsers;
}
