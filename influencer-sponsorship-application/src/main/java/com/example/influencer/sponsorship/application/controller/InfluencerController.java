package com.example.influencer.sponsorship.application.controller;

import com.example.influencer.sponsorship.application.dto.InfluencerDto;
import com.example.influencer.sponsorship.application.service.InfluencerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/influencers")

public class InfluencerController {

    private final InfluencerService influencerService;

    public InfluencerController(InfluencerService influencerService)  {
        this.influencerService = influencerService;
    }

    @PostMapping
    public ResponseEntity<InfluencerDto> createInfluencer(
            @Valid @RequestBody InfluencerDto influencerDto) {
        InfluencerDto createdInfluencer = influencerService.createInfluencer(influencerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInfluencer);
    }

    @GetMapping
    public ResponseEntity<Page<InfluencerDto>> getAllInfluencers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort

    ) {
        Sort sortOrder = buildSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<InfluencerDto> influencers = influencerService.getAllInfluencers(pageable);
        return ResponseEntity.ok(influencers);
    }

    @PatchMapping("/{influencerId}")
    public ResponseEntity<InfluencerDto> updateInfluencer(
            @PathVariable Long influencerId, @Valid @RequestBody InfluencerDto dto
    ) {
        return ResponseEntity.ok(influencerService.updateInfluencer(influencerId, dto));
    }

    @GetMapping("/{influencerId}")
    public ResponseEntity<InfluencerDto> getInfluencerById(@PathVariable Long influencerId) {
        InfluencerDto influencer =  influencerService.getInfluencerById(influencerId);
        return ResponseEntity.ok(influencer);
    }

    @DeleteMapping("/{influencerId}")
    public ResponseEntity<Void>  deleteInfluencerById(@PathVariable Long influencerId) {
        influencerService.deleteInfluencer(influencerId);
        return ResponseEntity.noContent().build();

    }

    private Sort buildSort(String[] sort) {
        if (sort.length == 0 || sort[0].isEmpty()) {
            return Sort.by(Sort.Direction.DESC, "id");
        }

        String[] sortParams = sort[0].split(",");
        String field = sortParams[0];

        Sort.Direction direction = Sort.Direction.DESC;
        if (sortParams.length > 1) {
            direction = sortParams[1].equalsIgnoreCase("asc")
                    ? Sort.Direction.ASC
                    : Sort.Direction.DESC;
        }

        return Sort.by(direction, field);
    }

}
