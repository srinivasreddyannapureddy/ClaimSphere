package com.claimsphere.auth.service.impl;

import com.claimsphere.auth.dto.AppUserSearchRequest;
import com.claimsphere.auth.entity.AppUser;
import com.claimsphere.auth.repository.AppUserRepository;
import com.claimsphere.auth.service.AppUserService;
import com.claimsphere.auth.specification.AppUserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    @Cacheable(value = "AppUser", key = "#request.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<AppUser> searchUsers(AppUserSearchRequest request,
                                     Pageable pageable) {

        log.info("Searching users with request: {}", request);
        Specification<AppUser> spec = Specification.unrestricted();

        if (request.getName() != null && !request.getName().isBlank()) {
            spec = spec.and(AppUserSpecification.hasName(request.getName()));
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            spec = spec.and(AppUserSpecification.hasEmail(request.getEmail()));
        }

        if (request.getRole() != null) {
            spec = spec.and(AppUserSpecification.hasRole(request.getRole()));
        }

        if (request.getProvider() != null) {
            spec = spec.and(AppUserSpecification.hasProvider(request.getProvider()));
        }

        if (request.getEnabled() != null) {
            spec = spec.and(AppUserSpecification.isEnabled(request.getEnabled()));
        }

        return appUserRepository.findAll(spec, pageable);
    }
}