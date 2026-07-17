package com.claimsphere.auth.dto;

import com.claimsphere.auth.entity.AuthProvider;
import com.claimsphere.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserSearchRequest {

    private String name;

    private String email;

    private Role role;

    private AuthProvider provider;

    private Boolean enabled;

    private LocalDateTime createdAfter;

    private LocalDateTime createdBefore;
}
