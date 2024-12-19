package com.technical.assessment.customer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customer")
@Slf4j
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CustomerController(CustomerService customerService, KafkaTemplate<String, String> kafkaTemplate){
        this.customerService = customerService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        log.info("Fetching all customers");
        return customerService.getCustomers();
    }

    @Cacheable(value = "customers", key = "#customerId")
    @GetMapping("{customerId}")
    public Customer getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public void registerNewCustomer(@RequestBody Customer customer){
        log.info("Registering a new customer: {}", customer);
        customerService.addNewCustomer(customer);

        //publish kafka
        kafkaTemplate.send("technical.assessment", customer.toString());
    }

    @CachePut(value = "customers", key="#customerId")
    @PutMapping(path="{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String preferredName,
            @RequestParam(required = false) String email){
        log.info("Updating Customer: {}", customerId);
        customerService.updateCustomer(customerId, firstName, lastName, preferredName, email);
    }
}
