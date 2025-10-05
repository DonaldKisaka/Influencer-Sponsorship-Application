package com.example.influencer.sponsorship.application.dto;

import com.example.influencer.sponsorship.application.model.OfferStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record OfferDto (
        @NotNull(message = "Influencer Id cannot be null")
        Long influencerId,

        @NotNull(message = "Brand Id cannot be null")
        Long brandId,

        @NotNull(message = "Money Amount cannot be null")
        @DecimalMin(value = "0.0", message = "Money Amount cannot be negative")
        Double moneyAmount,

        OfferStatus status
) {}
