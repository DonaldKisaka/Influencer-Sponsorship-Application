package com.example.influencer.sponsorship.application.repository;

import com.example.influencer.sponsorship.application.model.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Page<Offer> findByInfluencerId(Long influencerId, Pageable pageable);
    Page<Offer> findByBrandId(Long brandId, Pageable pageable);
}
