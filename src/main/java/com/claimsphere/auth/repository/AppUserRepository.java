package com.claimsphere.auth.repository;

import com.claimsphere.auth.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long>,
        JpaSpecificationExecutor<AppUser> {

    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);
}