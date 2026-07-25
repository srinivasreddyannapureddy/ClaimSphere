package com.claimsphere.policy.repository;

import com.claimsphere.policy.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository
        extends JpaRepository<Policy, Long> {

    boolean existsByPolicyNumber(String policyNumber);
}