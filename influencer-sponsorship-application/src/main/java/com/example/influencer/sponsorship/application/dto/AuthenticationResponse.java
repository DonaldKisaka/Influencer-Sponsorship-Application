package com.example.influencer.sponsorship.application.dto;

public record AuthenticationResponse(
        String token,
        String username
) {}
