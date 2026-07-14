package com.claimsphere.common;

import com.claimsphere.auth.repository.AppUserRepository;
import com.claimsphere.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class HelloController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/hello")
    public ResponseEntity<ApiResponse<String>> hello() {

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Request successful")
                .data("Hello from ClaimSphere!")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello1")
    public String hello1() {

        appUserRepository.findAll().forEach(user -> {
            log.info("User: " + user.getEmail());
        });
        return "Hello from ClaimSphere!";
    }
}
