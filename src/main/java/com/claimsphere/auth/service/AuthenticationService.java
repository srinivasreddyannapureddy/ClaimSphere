package com.claimsphere.auth.service;

import com.claimsphere.auth.dto.AuthenticationResponse;
import com.claimsphere.auth.dto.LoginRequest;
import com.claimsphere.auth.dto.RegisterRequest;
import com.claimsphere.auth.entity.AppUser;
import com.claimsphere.auth.entity.AuthProvider;
import com.claimsphere.auth.entity.Role;
import com.claimsphere.auth.repository.AppUserRepository;
import com.claimsphere.auth.repository.RoleRepository;
import com.claimsphere.common.constants.RoleConstants;
import com.claimsphere.security.AppUserPrincipal;
import com.claimsphere.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {

        AppUser user = appUserRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {

            Role memberRole = roleRepository.findByName(RoleConstants.MEMBER)
                    .orElseThrow(() -> new RuntimeException("ROLE_MEMBER not found"));

            user = AppUser.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .provider(AuthProvider.LOCAL)
                    .googleLinked(false)
                    .enabled(true)
                    .accountLocked(false)
                    .build();

            user.getRoles().add(memberRole);

            appUserRepository.save(user);

        } else {

            // User already has a local account
            if (user.getPassword() != null) {
                throw new RuntimeException("User already exists.");
            }

            // Google account exists.
            // Enable local login by setting password.
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            appUserRepository.save(user);
        }

        String accessToken =
                jwtService.generateAccessToken(
                        new AppUserPrincipal(user));

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .expiresIn(3600L)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {

        AppUser user = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));

        // User registered only through Google
        if (user.getPassword() == null) {
            throw new RuntimeException(
                    "This account uses Google Sign-In. Please continue with Google."
            );
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String accessToken =
                jwtService.generateAccessToken(
                        new AppUserPrincipal(user));

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .expiresIn(3600L)
                .build();
    }

}