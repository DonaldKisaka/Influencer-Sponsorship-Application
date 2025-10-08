package com.example.influencer.sponsorship.application.controller;

import com.example.influencer.sponsorship.application.dto.BrandDto;
import com.example.influencer.sponsorship.application.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    public ResponseEntity<BrandDto> createBrand(@Valid @RequestBody  BrandDto dto) {
        BrandDto createdBrand = brandService.createBrand(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable Long id) {
        BrandDto brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDto> updateBrand(@Valid @PathVariable Long id, @RequestBody BrandDto dto) {
        BrandDto updatedBrand = brandService.updateBrand(id, dto);
        return ResponseEntity.ok(updatedBrand);
    }

    @GetMapping
    public ResponseEntity<Page<BrandDto>> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        Sort sortOrder = buildSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<BrandDto> brands = brandService.getAllBrands(pageable);
        return ResponseEntity.ok(brands);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrandById(@PathVariable Long id) {
        brandService.deleteBrand(id);
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
