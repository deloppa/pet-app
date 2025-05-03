package com.pet.pet_app.controller;

import com.pet.pet_app.model.AuthRequest;
import com.pet.pet_app.model.AuthResponse;
import com.pet.pet_app.model.User;
import com.pet.pet_app.repository.UserRepository;
import com.pet.pet_app.security.JwtSecurity;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtSecurity jwtSecurity;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>
    login(@RequestBody AuthRequest request) {
        Authentication authentication =
            authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));

        User user =
            userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtSecurity.generateToken(
            user.getUsername(), user.getRoles()
                                    .stream()
                                    .map(Enum::name)
                                    .collect(Collectors.toSet()));

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
