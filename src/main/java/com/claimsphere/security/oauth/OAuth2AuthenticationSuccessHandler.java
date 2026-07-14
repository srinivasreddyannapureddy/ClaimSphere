package com.claimsphere.security.oauth;

import com.claimsphere.auth.entity.AppUser;
import com.claimsphere.auth.repository.AppUserRepository;
import com.claimsphere.security.AppUserPrincipal;
import com.claimsphere.security.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        AppUser user = appUserRepository
                .findByEmail(oidcUser.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));

        String accessToken = jwtService.generateAccessToken(
                new AppUserPrincipal(user));

        //response.sendRedirect(
          //      "http://localhost:3000/oauth2/success?token=" + accessToken);

        //response.setContentType("text/plain");

        //response.getWriter().write(accessToken);

        System.out.println("Generated Access Token: " + accessToken);


        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);   // 1 hour
        cookie.setSecure(false);     // true when using HTTPS

        response.addCookie(cookie);
        response.sendRedirect(
                "http://localhost:8080/api/hello1");


    }
}