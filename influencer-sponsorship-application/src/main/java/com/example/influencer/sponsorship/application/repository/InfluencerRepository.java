package com.example.influencer.sponsorship.application.repository;

import com.example.influencer.sponsorship.application.model.Influencer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    Optional<Influencer> findByUsername(String username);
}
