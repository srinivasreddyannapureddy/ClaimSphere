package com.claimsphere.common.startup;

import com.claimsphere.auth.entity.Role;
import com.claimsphere.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        createRoleIfNotExists("ROLE_ADMIN", "System Administrator");
        createRoleIfNotExists("ROLE_MEMBER", "Insurance Member");
        createRoleIfNotExists("ROLE_PROVIDER", "Healthcare Provider");
        createRoleIfNotExists("ROLE_CLAIMS_PROCESSOR", "Claims Processor");
        createRoleIfNotExists("ROLE_AUDITOR", "Auditor");
    }

    private void createRoleIfNotExists(String name, String description) {

        if (roleRepository.findByName(name).isEmpty()) {

            roleRepository.save(
                    Role.builder()
                            .name(name)
                            .description(description)
                            .build()
            );
        }
    }
}