package com.claimsphere.auth.controller;

import com.claimsphere.auth.dto.AppUserSearchRequest;
import com.claimsphere.auth.entity.AppUser;
import com.claimsphere.auth.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public Page<AppUser> searchUsers(
            AppUserSearchRequest request,
            Pageable pageable) {

        return appUserService.searchUsers(request, pageable);
    }
}
