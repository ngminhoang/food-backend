package org.example.foodbackend.services;

import org.example.foodbackend.authentication.AuthenticationRequest;
import org.example.foodbackend.authentication.AuthenticationResponse;
import org.example.foodbackend.authentication.Register;
import org.example.foodbackend.configuration.JwtService;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.enums.Erole;
import org.example.foodbackend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthenticationResponse login(AuthenticationRequest request) {
        Optional<Account> optionalUser = accountRepository.findByMail(request.getMail());
        if (!optionalUser.isPresent()) {
            return null;
        }
        Account user = optionalUser.get();
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String jwt = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwt).build();
        }
        return null;
    }

    public AuthenticationResponse register(Register request) {
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Account user = Account.builder()
                .name(request.getName())
                .mail(request.getMail())
                .password(encryptedPassword)
                .role(Erole.ROLE_USER)
                .build();
        accountRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }
}
