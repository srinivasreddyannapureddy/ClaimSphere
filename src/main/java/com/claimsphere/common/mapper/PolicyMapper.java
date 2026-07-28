package com.claimsphere.common.mapper;

import com.claimsphere.policy.dto.PolicyRequestDTO;
import com.claimsphere.policy.dto.PolicyResponseDTO;
import com.claimsphere.policy.entity.Policy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PolicyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "claims", ignore = true)
    @Mapping(target = "status", ignore = true)
    Policy toEntity(PolicyRequestDTO requestDTO);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "totalClaims",
            expression = "java(policy.getClaims() == null ? 0 : policy.getClaims().size())")
    PolicyResponseDTO toResponseDTO(Policy policy);
}