package com.example.influencer.sponsorship.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUser(
        @NotBlank
        String username,

        @NotBlank
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {}
