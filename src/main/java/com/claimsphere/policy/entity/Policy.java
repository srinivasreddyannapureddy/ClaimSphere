package com.claimsphere.policy.entity;

import com.claimsphere.claim.entity.Claim;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String policyNumber;

    private String policyName;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal coverageAmount;

    @OneToMany(mappedBy = "policy")
    @Builder.Default
    private List<Claim> claims = new ArrayList<>();
}