package com.example.influencer.sponsorship.application.mapper;

import com.example.influencer.sponsorship.application.dto.InfluencerDto;
import com.example.influencer.sponsorship.application.model.Influencer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class InfluencerDtoMapper implements Function<Influencer, InfluencerDto> {
    @Override
    public InfluencerDto apply(Influencer influencer) {
        return new InfluencerDto(
                influencer.getId(),
                influencer.getUsername(),
                influencer.getSocialMediaPlatform(),
                influencer.getFollowers(),
                influencer.getEngagementRate(),
                influencer.getTotalEarnings()
        );
    }
}
