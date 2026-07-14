package com.claimsphere.security.service;

import com.claimsphere.auth.entity.AppUser;
import com.claimsphere.auth.entity.AuthProvider;
import com.claimsphere.auth.entity.Role;
import com.claimsphere.auth.repository.AppUserRepository;
import com.claimsphere.auth.repository.RoleRepository;
import com.claimsphere.common.constants.RoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest request)
            throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(request);

        String email = oidcUser.getEmail();

        AppUser user = appUserRepository
                .findByEmail(email)
                .orElse(null);

        if (user == null) {

            Role memberRole = roleRepository.findByName(RoleConstants.MEMBER)
                    .orElseThrow();

            user = AppUser.builder()
                    .firstName(oidcUser.getGivenName())
                    .lastName(oidcUser.getFamilyName())
                    .email(email)
                    .provider(AuthProvider.GOOGLE)
                    .providerId(oidcUser.getSubject())
                    .googleLinked(true)
                    .enabled(true)
                    .accountLocked(false)
                    .build();

            user.getRoles().add(memberRole);

            appUserRepository.save(user);

        } else {

            if (!user.isGoogleLinked()) {

                user.setGoogleLinked(true);

                user.setProviderId(oidcUser.getSubject());

                appUserRepository.save(user);
            }
        }

        return oidcUser;
    }

}