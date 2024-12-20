package org.example.foodbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodbackend.entities.enums.ELanguage;
import org.example.foodbackend.entities.enums.Erole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "mail", unique = true, nullable = false)
    private String mail;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "role")
    private Erole role;
    @Column
    private String avatar_url;
    @Column
    private ELanguage language;
    @ManyToMany
    @JoinTable(name = "user_tools", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private List<KitchenTool> tools;
    @ManyToMany
    @JoinTable(name = "user_spices", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "spice_id"))
    private List<KitchenSpice> spices;
    @OneToMany(mappedBy = "user")
    private List<UserIngredient> userIngredients;
    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Post post;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = role.name();
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getUsername() {
        return getMail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", name='" + name + "'}";  // Avoid listing lazy-loaded fields like listTools here
    }

}
