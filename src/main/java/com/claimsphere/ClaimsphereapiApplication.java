package com.claimsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ClaimsphereapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaimsphereapiApplication.class, args);
	}

}
