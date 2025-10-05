package com.example.influencer.sponsorship.application.mapper;

import com.example.influencer.sponsorship.application.dto.OfferDto;
import com.example.influencer.sponsorship.application.model.Offer;
import org.springframework.stereotype.Service;
import java.util.function.Function;


@Service
public class OfferDtoMapper implements Function<Offer, OfferDto> {
    @Override
    public OfferDto apply(Offer offer) {
        return new OfferDto(
                offer.getInfluencer().getId(),
                offer.getBrand().getId(),
                offer.getMoneyAmount(),
                offer.getStatus()
        );
    }
}
