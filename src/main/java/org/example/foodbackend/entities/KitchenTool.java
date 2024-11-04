package org.example.foodbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kitchen_tool")
public class KitchenTool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name_en;
    @Column
    private String name_vi;
    @Column(length = 500)
    private String img_url;
    @ManyToMany(mappedBy = "tools")
    @JsonIgnore
    private Set<Account> listUsers;
}
