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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
                .orElseThrow(() -> new IllegalArgumentException("Influencer not found with Id: " + dto.influencerId()));

        Brand brand = brandRepository.findById(dto.brandId())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found: " + dto.brandId()));

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

    @Transactional
    public OfferDto acceptOffer (Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found with Id"));

        if (offer.getStatus() != OfferStatus.PENDING) {
            throw new IllegalArgumentException("Offer has already been processed.");
        }

        Brand brand = offer.getBrand();
        if (brand.getBudget() < offer.getMoneyAmount()) {
            throw new IllegalArgumentException("Insufficient brand budget.");
        }

        offer.setStatus(OfferStatus.ACCEPTED);
        brand.setBudget(brand.getBudget() - offer.getMoneyAmount());
        brandRepository.save(brand);

        Influencer influencer = offer.getInfluencer();
        influencer.setTotalEarnings(influencer.getTotalEarnings() + offer.getMoneyAmount());
        influencerRepository.save(influencer);

        return offerDtoMapper.apply(offerRepository.save(offer));
    }

    @Transactional
    public OfferDto rejectOffer (Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found with Id"));

        if (offer.getStatus() != OfferStatus.PENDING) {
            throw new IllegalArgumentException("Offer has already been processed.");
        }

        offer.setStatus(OfferStatus.REJECTED);
        return offerDtoMapper.apply(offerRepository.save(offer));
    }

    public Page<OfferDto> getOffersByInfluencer(Long influencerId, Pageable pageable) {
        return offerRepository.findByInfluencerId(influencerId, pageable)
                .map(offerDtoMapper);
    }

    public Page<OfferDto> getOffersByBrand(Long brandId, Pageable pageable) {
        return offerRepository.findByBrandId(brandId, pageable)
                .map(offerDtoMapper);
    }

    public OfferDto getOfferById(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found with Id"));

        return offerDtoMapper.apply(offer);
    }
}
