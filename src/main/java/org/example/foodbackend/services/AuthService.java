package org.example.foodbackend.services;

import org.example.foodbackend.authentication.AuthenticationRequest;
import org.example.foodbackend.authentication.AuthenticationResponse;
import org.example.foodbackend.authentication.Register;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.entities.dto.UserInfoDTO;
import org.example.foodbackend.entities.enums.ELanguage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse register(Register request);

    List<KitchenTool> getUserInfo(Long userId);
    ResponseEntity<UserInfoDTO> updateUserInfo(Long userId, UserInfoDTO userInfoDTO);
    ResponseEntity<?> updateUserLanguage(Long userId, ELanguage language);
}
