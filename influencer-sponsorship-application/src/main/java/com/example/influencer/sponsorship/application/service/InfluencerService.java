package com.example.influencer.sponsorship.application.service;

import com.example.influencer.sponsorship.application.dto.InfluencerDto;
import com.example.influencer.sponsorship.application.mapper.InfluencerDtoMapper;
import com.example.influencer.sponsorship.application.model.Influencer;
import com.example.influencer.sponsorship.application.repository.InfluencerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;
    private final InfluencerDtoMapper influencerDtoMapper;

    public InfluencerService(InfluencerRepository influencerRepository, InfluencerDtoMapper influencerDtoMapper) {
        this.influencerRepository = influencerRepository;
        this.influencerDtoMapper = influencerDtoMapper;
    }

    @Transactional
    public InfluencerDto createInfluencer(InfluencerDto dto) {
        Influencer influencer = Influencer.builder()
                .username(dto.username())
                .socialMediaPlatform(dto.socialMediaPlatform())
                .followers(dto.followers())
                .engagementRate(dto.engagementRate())
                .totalEarnings(0.0)
                .build();
        return influencerDtoMapper.apply(influencerRepository.save(influencer));
    }

    public Page<InfluencerDto> getAllInfluencers(Pageable pageable) {
        return influencerRepository.findAll(pageable)
                .map(influencerDtoMapper);
    }

    public InfluencerDto getInfluencerById(Long id) {
        Influencer influencer = influencerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Influencer not found with Id: " + id));
        return influencerDtoMapper.apply(influencer);
    }

    @Transactional
    public InfluencerDto updateInfluencer(Long id, InfluencerDto dto) {
        Influencer influencer = influencerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Influencer not found with Id: " + id));

        influencer.setSocialMediaPlatform(dto.socialMediaPlatform());
        influencer.setFollowers(dto.followers());
        influencer.setEngagementRate(dto.engagementRate());

        return influencerDtoMapper.apply(influencerRepository.save(influencer));
    }

    @Transactional
    public void deleteInfluencer(Long id) {
        if (!influencerRepository.existsById(id)) {
            throw new IllegalArgumentException("Influencer not found with Id: " + id);
        }
        influencerRepository.deleteById(id);
    }
}
