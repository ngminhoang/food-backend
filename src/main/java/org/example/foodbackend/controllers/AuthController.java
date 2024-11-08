package org.example.foodbackend.controllers;

import org.example.foodbackend.authentication.AuthenticationRequest;
import org.example.foodbackend.authentication.AuthenticationResponse;
import org.example.foodbackend.authentication.Register;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.dto.UserInfoDTO;
import org.example.foodbackend.entities.dto.UserLanguageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface AuthController {
    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    );

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(
            @RequestBody Register request
    );

    @GetMapping("/user/info")
    ResponseEntity<UserInfoDTO> getUserInfo(@AuthenticationPrincipal Account account);

    @PostMapping("/user/info")
    ResponseEntity<UserInfoDTO> updateUserInfo(@AuthenticationPrincipal Account account, @RequestBody UserInfoDTO userInfoDTO);

    @PostMapping("user/info/language")
    ResponseEntity<?> updateUserInfoLanguage(@AuthenticationPrincipal Account account, @RequestBody UserLanguageDTO userLanguageDTO);
}
