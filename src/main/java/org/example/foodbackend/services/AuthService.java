package org.example.foodbackend.services;

import org.example.foodbackend.authentication.AuthenticationRequest;
import org.example.foodbackend.authentication.AuthenticationResponse;
import org.example.foodbackend.authentication.Register;

public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse register(Register request);
}
