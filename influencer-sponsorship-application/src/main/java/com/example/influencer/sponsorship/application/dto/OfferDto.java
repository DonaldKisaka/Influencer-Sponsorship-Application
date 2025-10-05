package com.example.influencer.sponsorship.application.dto;

import com.example.influencer.sponsorship.application.model.OfferStatus;

public record OfferDto (
        Long influencerId,
        Long brandId,
        Double moneyAmount,
        OfferStatus status
) {}
