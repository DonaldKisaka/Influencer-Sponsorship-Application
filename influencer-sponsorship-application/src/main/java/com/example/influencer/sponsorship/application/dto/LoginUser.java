package com.example.influencer.sponsorship.application.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginUser(
        @NotBlank
        String username,

        @NotBlank
        String password
) {}
