package org.example.foodbackend.authentication;

import lombok.Builder;
import lombok.Data;
import org.example.foodbackend.entities.Erole;

@Builder
@Data
public class Register {
    private String mail;
    private String password;
    private String name;
    private Erole role;
}
