package com.example.influencer.sponsorship.application.controller;

import com.example.influencer.sponsorship.application.dto.AuthenticationResponse;
import com.example.influencer.sponsorship.application.dto.CreateUser;
import com.example.influencer.sponsorship.application.dto.LoginUser;
import com.example.influencer.sponsorship.application.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(
            @Valid @RequestBody CreateUser input
    ) {
        AuthenticationResponse response = authenticationService.signup(input);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signup(
            @Valid @RequestBody LoginUser input
    ) {
        AuthenticationResponse response = authenticationService.authenticate(input);
        return ResponseEntity.ok(response);
    }
}
