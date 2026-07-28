package com.claimsphere.policy.entity;

import com.claimsphere.claim.entity.Claim;
import com.claimsphere.customer.entity.Customer;
import com.claimsphere.policy.enums.PolicyStatus;
import com.claimsphere.policy.enums.PolicyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique =true)
    private String policyNumber;

    @Column(nullable = false)
    private String policyName;

    @Enumerated(EnumType.STRING)
    private PolicyType policyType;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal coverageAmount;

    private BigDecimal premium;

    @Enumerated(EnumType.STRING)
    private PolicyStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Claim> claims = new ArrayList<>();
}