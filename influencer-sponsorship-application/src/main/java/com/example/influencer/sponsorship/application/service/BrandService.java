package com.example.influencer.sponsorship.application.service;

import com.example.influencer.sponsorship.application.dto.BrandDto;
import com.example.influencer.sponsorship.application.mapper.BrandDtoMapper;
import com.example.influencer.sponsorship.application.model.Brand;
import com.example.influencer.sponsorship.application.repository.BrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandDtoMapper brandDtoMapper;

    public BrandService(BrandRepository brandRepository, BrandDtoMapper brandDtoMapper) {
        this.brandRepository = brandRepository;
        this.brandDtoMapper = brandDtoMapper;
    }


    @Transactional
    public BrandDto createBrand(BrandDto dto) {
        Brand brand = Brand.builder()
                .name(dto.name())
                .budget(dto.budget())
                .build();
        return brandDtoMapper.apply(brandRepository.save(brand));
    }

    public BrandDto getBrandById(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + brandId));
        return brandDtoMapper.apply(brand);
    }

    @Transactional
    public BrandDto updateBrand(Long brandId, BrandDto dto) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + brandId));
        brand.setName(dto.name());
        brand.setBudget(dto.budget());
        return brandDtoMapper.apply(brandRepository.save(brand));
    }

    public Page<BrandDto> getAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable)
                .map(brandDtoMapper);
    }


    @Transactional
    public void deleteBrand(Long brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new IllegalArgumentException("Brand not found with id: " + brandId);
        }
        brandRepository.deleteById(brandId);
    }
}
