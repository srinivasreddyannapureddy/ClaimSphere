package com.claimsphere.claim.repository;

import com.claimsphere.claim.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository
        extends JpaRepository<Claim, Long> {
}