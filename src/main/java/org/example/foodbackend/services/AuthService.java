package org.example.foodbackend.services;

import org.example.foodbackend.authentication.AuthenticationRequest;
import org.example.foodbackend.authentication.AuthenticationResponse;
import org.example.foodbackend.authentication.Register;
import org.example.foodbackend.entities.KitchenTool;

import java.util.List;

public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse register(Register request);

    List<KitchenTool> getUserInfo(Long userId);
}
