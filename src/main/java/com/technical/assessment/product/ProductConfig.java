package com.technical.assessment.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class ProductConfig {

    @Profile("product")
    @Bean
    CommandLineRunner productRunner(ProductRepository repository) {
        return args -> {
            Product silent = new Product(
                    "Silent Hill 4",
                    "Luqman",
                    10.00,
                    "horror",
                    5.00,
                    1
            );

            Product resident = new Product(
                    "Resident Evil 4",
                    "Uzma",
                    50.00,
                    "horror",
                    5.00,
                    10
            );

            repository.saveAll(
                    List.of(silent, resident)
            );
        };
    }
}
