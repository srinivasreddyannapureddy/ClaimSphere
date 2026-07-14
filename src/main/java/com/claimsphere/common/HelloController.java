package com.claimsphere.common;

import com.claimsphere.auth.repository.AppUserRepository;
import com.claimsphere.security.AppUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from ClaimSphere!";
    }

    @GetMapping("/hello1")
    public String hello1() {

        appUserRepository.findAll().forEach(user -> {
            System.out.println("User: " + user.getEmail());
        });
        return "Hello from ClaimSphere!";
    }
}
