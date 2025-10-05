package com.example.influencer.sponsorship.application.controller;

import com.example.influencer.sponsorship.application.dto.OfferDto;
import com.example.influencer.sponsorship.application.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<OfferDto> createOffer (
            @Valid @RequestBody OfferDto dto
    ) {
        OfferDto createdOffer = offerService.createOffer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
    }

    @PatchMapping("/{offerId}/reject")
    public ResponseEntity<OfferDto> rejectOffer (@PathVariable Long offerId) {
        OfferDto rejectedOffer = offerService.rejectOffer(offerId);
        return ResponseEntity.ok(rejectedOffer);
    }

    @PatchMapping("/{offerId}/accept")
    public ResponseEntity<OfferDto> acceptOffer (@PathVariable Long offerId) {
        OfferDto acceptedOffer = offerService.acceptOffer(offerId);
        return ResponseEntity.ok(acceptedOffer);
    }

    @GetMapping("/influencer/{influencerId}")
    public ResponseEntity<Page<OfferDto>> getOffersByInfluencer (
            @PathVariable Long influencerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        Sort sortOrder = buildSort(sort);

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<OfferDto> offers = offerService.getOffersByInfluencer(influencerId, pageable);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<Page<OfferDto>> getOffersByBrand(
            @PathVariable Long brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        Sort sortOrder = buildSort(sort);

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<OfferDto> offers = offerService.getOffersByBrand(brandId, pageable);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<OfferDto> getOfferById(@PathVariable Long offerId) {
        OfferDto offer = offerService.getOfferById(offerId);
        return ResponseEntity.ok(offer);
    }

    private Sort buildSort(String[] sort) {
        if (sort.length == 0 || sort[0].isEmpty()) {
            return Sort.by(Sort.Direction.DESC, "id");  // Default sort
        }

        String[] sortParams = sort[0].split(",");
        String field = sortParams[0];

        Sort.Direction direction = Sort.Direction.DESC;  // Default direction
        if (sortParams.length > 1) {
            direction = sortParams[1].equalsIgnoreCase("asc")
                    ? Sort.Direction.ASC
                    : Sort.Direction.DESC;
        }

        return Sort.by(direction, field);
    }
}
