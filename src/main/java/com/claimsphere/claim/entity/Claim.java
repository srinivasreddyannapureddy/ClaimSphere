package com.claimsphere.claim.entity;


import com.claimsphere.claim.enums.ClaimStatus;
import com.claimsphere.claim.enums.ClaimType;
import com.claimsphere.customer.entity.Customer;
import com.claimsphere.policy.entity.Policy;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    @Enumerated(EnumType.STRING)
    private ClaimType claimType;

    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private Policy policy;
}
