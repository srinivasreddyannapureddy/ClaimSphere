package com.claimsphere.claim.controller;

import com.claimsphere.claim.dto.ClaimRequestDTO;
import com.claimsphere.claim.dto.ClaimResponseDTO;
import com.claimsphere.claim.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    public ResponseEntity<ClaimResponseDTO> createClaim(
            @Valid @RequestBody ClaimRequestDTO requestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(claimService.createClaim(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimResponseDTO> getClaimById(
            @PathVariable Long id) {

        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClaimResponseDTO>> getAllClaims() {

        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaimResponseDTO> updateClaim(
            @PathVariable Long id,
            @Valid @RequestBody ClaimRequestDTO requestDTO) {

        return ResponseEntity.ok(
                claimService.updateClaim(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(
            @PathVariable Long id) {

        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }
}
