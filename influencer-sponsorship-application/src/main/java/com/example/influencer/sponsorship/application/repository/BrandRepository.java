package com.example.influencer.sponsorship.application.repository;

import com.example.influencer.sponsorship.application.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
