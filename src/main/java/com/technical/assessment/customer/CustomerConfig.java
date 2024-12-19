package com.technical.assessment.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Profile("customer")
    @Bean
    CommandLineRunner customerRunner(CustomerRepository repository) {
        return args -> {
            Customer najib = new Customer(
                    "Najib",
                    "Razak",
                    "YAB Najib Razak",
                    "jibby.co@gmail.com",
                    LocalDate.of(2003, Month.JANUARY, 5)
            );

            Customer rosmah = new Customer(
                    "Rosmah",
                    "Mansur",
                    "Datin Rosmah",
                    "rosmah@gmail.com",
                    LocalDate.of(2005, Month.JANUARY, 5)
            );

            repository.saveAll(
                    List.of(najib, rosmah)
            );
        };
    }
}
