package com.claimsphere.customer.entity;

import com.claimsphere.policy.entity.Policy;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;

    private String phone;

    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Policy> policies = new ArrayList<>();

    public void addPolicy(Policy policy) {

        if (policies == null) {
            policies = new ArrayList<>();
        }

        policies.add(policy);
        policy.setCustomer(this);
    }



    public void removePolicy(Policy policy) {
        policies.remove(policy);
        policy.setCustomer(null);
    }

}