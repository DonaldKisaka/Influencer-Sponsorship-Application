package com.example.influencer.sponsorship.application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "influencer")
public class Influencer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Column(nullable = false)
    private String password;

    @NotBlank
    private String socialMediaPlatform;


    @NotBlank
    @Min(value = 0, message = "Followers cannot be negative")
    private Long followers;

    @NotBlank
    @DecimalMin(value = "0.0", message = "Engagement rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Engagement rate cannot be greater than 1")
    private Double engagementRate;

    private boolean enabled;


    public Influencer(String username, String password, String socialMediaPlatform, Long followers, Double engagementRate) {
        this.username = username;
        this.password = password;
        this.socialMediaPlatform = socialMediaPlatform;
        this.followers = followers;
        this.engagementRate = engagementRate;
    }

    public Influencer() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
