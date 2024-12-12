package org.example.foodbackend.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.enums.Erole;

@Data
@AllArgsConstructor
public class AccountResponseDTO {
    private Long id;
    private String mail;
    private String name;
    private Erole role;

    public AccountResponseDTO(Account account) {
        this.id = account.getId();
        this.mail = account.getMail();
        this.name = account.getName();
        this.role = account.getRole();
    }
}

