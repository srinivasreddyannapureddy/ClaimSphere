package com.claimsphere.auth.service;


import com.claimsphere.auth.dto.AppUserSearchRequest;
import com.claimsphere.auth.entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppUserService {

    Page<AppUser> searchUsers(AppUserSearchRequest request,
                              Pageable pageable);

}