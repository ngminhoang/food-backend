package org.example.foodbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kitchen_spice")
@Entity
public class KitchenSpice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name_en;
    @Column
    private String name_vi;
    @Column(length = 500)
    private String img_url;
    @ManyToMany(mappedBy = "spices")
    @JsonIgnore
    private Set<Account> users;
    @ManyToMany(mappedBy = "spices")
    @JsonIgnore
    private Set<Post> posts;
}