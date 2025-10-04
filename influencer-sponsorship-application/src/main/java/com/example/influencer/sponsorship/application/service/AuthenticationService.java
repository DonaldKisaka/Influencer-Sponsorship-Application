package com.example.influencer.sponsorship.application.service;

import com.example.influencer.sponsorship.application.config.JwtService;
import com.example.influencer.sponsorship.application.dto.AuthenticationResponse;
import com.example.influencer.sponsorship.application.dto.CreateUser;
import com.example.influencer.sponsorship.application.dto.LoginUser;
import com.example.influencer.sponsorship.application.model.Influencer;
import com.example.influencer.sponsorship.application.repository.InfluencerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final InfluencerRepository influencerRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationService(InfluencerRepository influencerRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.influencerRepository = influencerRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse signup (CreateUser input) {
        if (influencerRepository.findByUsername(input.username()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        Influencer influencer = new Influencer(
                input.username(),
                passwordEncoder.encode(input.password())
        );

        influencer.setEnabled(true);
        influencerRepository.save(influencer);

        String jwtToken = jwtService.generateToken(influencer);


        return new AuthenticationResponse(jwtToken, influencer.getUsername());
    }

    public AuthenticationResponse authenticate(LoginUser input) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )

        );

        Influencer influencer = influencerRepository.findByUsername(input.username()).orElseThrow(() -> new IllegalArgumentException("User not found."));

        influencer.setEnabled(true);

        String jwtToken = jwtService.generateToken(influencer);

        return new AuthenticationResponse(jwtToken, influencer.getUsername());
    }



}
