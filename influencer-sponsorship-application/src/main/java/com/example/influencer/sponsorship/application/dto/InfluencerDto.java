package com.example.influencer.sponsorship.application.dto;

public record InfluencerDto(
        Long id,
        String username,
        String socialMediaPlatform,
        Long followers,
        Double engagementRate,
        Double totalEarnings
) {}
