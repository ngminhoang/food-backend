package org.example.foodbackend.services;

import org.example.foodbackend.authentication.AuthenticationRequest;
import org.example.foodbackend.authentication.AuthenticationResponse;
import org.example.foodbackend.authentication.Register;
import org.example.foodbackend.configuration.JwtService;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.entities.dto.UserInfoDTO;
import org.example.foodbackend.entities.enums.ELanguage;
import org.example.foodbackend.entities.enums.Erole;
import org.example.foodbackend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
                .language(ELanguage.vi)
                .build();
        accountRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    public List<KitchenTool> getUserInfo(Long userId) {
        Account user = accountRepository.findById(userId).get();
        return user.getTools().stream().toList();
    }

    @Override
    public ResponseEntity<UserInfoDTO> updateUserInfo(Long userId, UserInfoDTO userInfoDTO) {
        try {
            Account user = accountRepository.findById(userId).get();
            user.setName(userInfoDTO.getName());
            user.setMail(userInfoDTO.getMail());
            user.setAvatar_url(userInfoDTO.getAvatar_url());
            accountRepository.save(user);
            return ResponseEntity.ok().body(userInfoDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<?> updateUserLanguage(Long userId, ELanguage language) {
        try {
            Account account = accountRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            account.setLanguage(language);
            accountRepository.save(account);
            return ResponseEntity.ok().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
