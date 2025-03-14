package org.example.foodbackend.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.example.foodbackend.authentication.AuthenticationRequest;
import org.example.foodbackend.authentication.AuthenticationResponse;
import org.example.foodbackend.authentication.GoogleAuthDTO;
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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
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
                .slots(1)
                .build();
        accountRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public ResponseEntity<AuthenticationResponse> loginGoogle(GoogleAuthDTO request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList("681928462243-9f8ssm2n368m0qn70e4dgfn43pnivshr.apps.googleusercontent.com"))
                    .build();
            String token = request.getIdToken();
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                Optional<Account> accountOptional = accountRepository.findByMail(email);
                if (accountOptional.isPresent()) {
                    Account user = accountOptional.get();
                    return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtService.generateToken(user)).build());
                } else {
                    String encryptedPassword = passwordEncoder.encode((String) payload.get("sub"));
                    Account newUser = Account.builder()
                            .name(name)
                            .avatar_url(pictureUrl)
                            .mail(email)
                            .password(encryptedPassword)
                            .role(Erole.ROLE_USER)
                            .language(ELanguage.vi)
                            .slots(1)
                            .build();
                    accountRepository.save(newUser);
                    return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtService.generateToken(newUser)).build());
                }
            }
            return ResponseEntity.ok(AuthenticationResponse.builder().token(token).build());
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.badRequest().build();
        }
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

    @Override
    public ResponseEntity<?> updateUserSlots(Long userId, int slots) {
        try {
            Account account = accountRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            account.setSlots(slots);
            accountRepository.save(account);
            return ResponseEntity.ok().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
