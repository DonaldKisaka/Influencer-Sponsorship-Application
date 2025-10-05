package com.example.influencer.sponsorship.application.service;

import com.example.influencer.sponsorship.application.dto.OfferDto;
import com.example.influencer.sponsorship.application.mapper.OfferDtoMapper;
import com.example.influencer.sponsorship.application.model.Brand;
import com.example.influencer.sponsorship.application.model.Influencer;
import com.example.influencer.sponsorship.application.model.Offer;
import com.example.influencer.sponsorship.application.model.OfferStatus;
import com.example.influencer.sponsorship.application.repository.BrandRepository;
import com.example.influencer.sponsorship.application.repository.InfluencerRepository;
import com.example.influencer.sponsorship.application.repository.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    private final InfluencerRepository influencerRepository;

    private final OfferRepository offerRepository;

    private final BrandRepository brandRepository;

    private final OfferDtoMapper offerDtoMapper;

    public OfferService(OfferRepository offerRepository, BrandRepository brandRepository, InfluencerRepository influencerRepository, OfferDtoMapper offerDtoMapper) {
        this.offerRepository = offerRepository;
        this.brandRepository = brandRepository;
        this.influencerRepository = influencerRepository;
        this.offerDtoMapper = offerDtoMapper;
    }


    @Transactional
    public OfferDto createOffer (OfferDto dto) {
        Influencer influencer = influencerRepository.findById(dto.influencerId())
                .orElseThrow(() -> new IllegalArgumentException("Influencer not found with Id"));

        Brand brand = brandRepository.findById(dto.brandId())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));

        if (brand.getBudget() < dto.moneyAmount()) {
            throw new IllegalArgumentException("Brand budget is not enough.");
        }

        Offer offer = Offer.builder()
                .influencer(influencer)
                .brand(brand)
                .moneyAmount(dto.moneyAmount())
                .status(OfferStatus.PENDING)
                .build();
        return offerDtoMapper.apply(offerRepository.save(offer));




    }
}
