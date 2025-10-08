package com.example.influencer.sponsorship.application.mapper;

import com.example.influencer.sponsorship.application.dto.BrandDto;
import com.example.influencer.sponsorship.application.model.Brand;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BrandDtoMapper implements Function<Brand, BrandDto> {

    @Override
    public BrandDto apply(Brand brand) {
        return new BrandDto(
                brand.getId(),
                brand.getName(),
                brand.getBudget()
        );
    }
}
