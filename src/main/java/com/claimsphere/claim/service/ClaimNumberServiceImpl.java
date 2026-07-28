package com.claimsphere.claim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Year;


    @Service
    @RequiredArgsConstructor
    public class ClaimNumberServiceImpl implements ClaimNumberService {

        private final JdbcTemplate jdbcTemplate;

        @Override
        public String generateClaimNumber() {

            Long sequence = jdbcTemplate.queryForObject(
                    "SELECT NEXT VALUE FOR claim_number_seq",
                    Long.class);

            return String.format("CLM-%d-%06d",
                    Year.now().getValue(),
                    sequence);
        }
    }

