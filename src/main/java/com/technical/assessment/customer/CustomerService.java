package com.technical.assessment.customer;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        log.debug("Retrieving all customers from the database");
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Customer with ID: " + id + " not found"));
    }

    public void addNewCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository
                .findCustomerByEmail(customer.getEmail());
        if (customerOptional.isPresent()) {
            throw new IllegalStateException("Email was taken");
        }
        log.info("Adding a new customer: {}", customer);
        customerRepository.save(customer);
    }

    @Transactional
    public void updateCustomer(
            Long customerId,
            String firstName,
            String lastName,
            String preferredName,
            String email){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException(
                        "customer with id " + customerId + " does not exist"));

        if (firstName != null &&
                !firstName.isEmpty() && !Objects.equals(customer.getFirstName(), firstName)) {
            customer.setFirstName(firstName);
        }

        if (lastName != null &&
                !lastName.isEmpty() && !Objects.equals(customer.getLastName(), lastName)) {
            customer.setLastName(lastName);
        }

        if (preferredName != null &&
                !preferredName.isEmpty() && !Objects.equals(customer.getPreferredName(), preferredName)) {
            customer.setPreferredName(preferredName);
        }

        if (email != null &&
                !email.isEmpty() && !Objects.equals(customer.getEmail(), email)) {
            Optional<Customer> customerOptional = customerRepository
                    .findCustomerByEmail(email);
            if (customerOptional.isPresent()){
                throw new IllegalStateException("Email was taken");
            }
            customer.setEmail(email);
        }

    }
}
