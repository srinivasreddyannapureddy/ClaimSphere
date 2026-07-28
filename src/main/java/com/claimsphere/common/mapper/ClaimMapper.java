package com.claimsphere.common.mapper;

import com.claimsphere.claim.dto.ClaimRequestDTO;
import com.claimsphere.claim.dto.ClaimResponseDTO;
import com.claimsphere.claim.entity.Claim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "claimNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "policy", ignore = true)
    Claim toEntity(ClaimRequestDTO dto);

    @Mapping(target = "policyId", source = "policy.id")
    @Mapping(target = "policyNumber", source = "policy.policyNumber")
    ClaimResponseDTO toResponseDTO(Claim claim);
}