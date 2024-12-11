package org.example.foodbackend.controllers;

import org.example.foodbackend.authentication.AuthenticationRequest;
import org.example.foodbackend.authentication.AuthenticationResponse;
import org.example.foodbackend.authentication.GoogleAuthDTO;
import org.example.foodbackend.authentication.Register;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.dto.UserInfoDTO;
import org.example.foodbackend.entities.dto.UserLanguageDTO;
import org.example.foodbackend.entities.dto.UserSlotDTO;
import org.example.foodbackend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Autowired
    private AuthService authService;

    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse res = authService.login(request);
        if (res != null) {
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().body(null);
    }

    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody Register request
    ) {
        AuthenticationResponse res = authService.register(request);
        if (res == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> loginGoogle(GoogleAuthDTO request) {
        return authService.loginGoogle(request);
    }

    @Override
    public ResponseEntity<UserInfoDTO> getUserInfo(Account account) {
        return ResponseEntity.ok(UserInfoDTO.builder()
                .id(account.getId())
                .mail(account.getMail())
                .name(account.getName())
                .avatar_url(account.getAvatar_url())
                .slots(account.getSlots())
                .language(account.getLanguage())
                .build());
    }

    @Override
    public ResponseEntity<UserInfoDTO> updateUserInfo(Account account, UserInfoDTO userInfoDTO) {
        return authService.updateUserInfo(account.getId(), userInfoDTO);
    }

    @Override
    public ResponseEntity<?> updateUserInfoLanguage(Account account, UserLanguageDTO userLanguageDTO) {
        return authService.updateUserLanguage(account.getId(), userLanguageDTO.getLanguage());
    }

    @Override
    public ResponseEntity<?> updateUserSlots(Account account, UserSlotDTO userSlotDTO) {
        return authService.updateUserSlots(account.getId(), userSlotDTO.getSlots());
    }

}
