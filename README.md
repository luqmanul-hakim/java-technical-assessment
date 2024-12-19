# Project Name
Spring Boot Technical Assessment
## Overview

1.  **Customer (Customer Class):**
The class is designed to manage customer data in a relational database and allows for CRUD operations while calculating the customer’s age dynamically based on their date of birth.

2.  **CustomerConfig (CustomerConfig Class):**
The CustomerConfig class is designed to pre-populate the database with customer data during application startup, but only when the customer profile is active. It uses Spring Boot's CommandLineRunner to execute this logic.

3.  **CustomerController(CustomerController Class):**
The CustomerController class provides a RESTful interface for managing customers. It includes basic CRUD operations for customers, utilizes caching to optimize data retrieval, and integrates with Kafka to asynchronously publish customer data for further processing. The class also implements logging to track actions and operations.

4. **CustomerRepository(CustomerRepository Interface):**
The CustomerRepository interface extends Spring Data JPA's JpaRepository to provide CRUD functionality for managing Customer entities. Additionally, it defines a custom query method findCustomerByEmail(String email) that allows you to search for customers by their email address using a JPQL query. This makes the repository an essential part of the data access layer in the application.

5. **CustomerService(CustomerService Class):**
The CustomerService class is the core service layer for managing Customer entities in the application. It provides methods for retrieving all customers, getting a customer by ID, adding new customers, and updating customer details. It ensures data integrity by validating email uniqueness and field changes before updating customer records. Additionally, it uses Spring's @Transactional annotation to manage transactions and ensure changes are committed atomically.

6. **KafkaConsumerConfig(KafkaConsumerConfig Class):**
The KafkaConsumerConfig class sets up the necessary configurations for Kafka consumers in a Spring Boot application. It defines how Kafka consumer instances are created, the configurations for consuming messages (like bootstrap servers and deserializers), and a factory for handling concurrent consumption of messages from Kafka topics. This enables the application to integrate smoothly with Kafka for message consumption.

7. **KafkaListeners(KafkaListeners Class):**
The KafkaListeners class listens to the Kafka topic "technical.assessment" and processes incoming messages using the @KafkaListener annotation. The messages are processed by the listener method, which prints the received message to the console. This is a simple implementation for message handling in a Kafka consumer setup in a Spring Boot application.

8. **KafkaProducerConfig(KafkaProducerConfig Class):**
The KafkaProducerConfig class is a Spring configuration class that sets up Kafka producer beans, such as ProducerFactory and KafkaTemplate. The producerConfig() method defines the producer configuration, including Kafka bootstrap servers and serializers. The producerFactory() method creates a factory for the Kafka producer based on the configuration. The kafkaTemplate() method creates a KafkaTemplate bean, which provides a convenient way to send messages to Kafka topics. This configuration ensures that the application can interact with Kafka as a producer, allowing it to send messages to specified topics.

9. **KafkaTopicConfig(KafkaTopicConfig Class):**The KafkaTopicConfig class is a Spring configuration class that defines Kafka topics for the application. It contains a restTopic() method which creates a new Kafka topic named "technical.assessment" using the TopicBuilder. This ensures that the application has the required Kafka topic for message production and consumption. The @Bean annotation marks the method as a bean, allowing it to be automatically registered in the Spring context, enabling Kafka producers and consumers to interact with the topic.

10. **WebConfig(WebConfig Class):**The WebConfig class is a Spring configuration class that implements the WebMvcConfigurer interface to customize Spring MVC's configuration. It registers a custom LoggingInterceptor that logs incoming requests. The addInterceptors() method adds the LoggingInterceptor to the application's interceptor registry, applying it to all incoming paths ("/**"). This configuration ensures that the LoggingInterceptor is invoked for every request, enabling logging for all HTTP requests processed by the Spring application.

11. **LoggingInterceptor(LoggingInterceptor Class):**
The WebConfig class is a Spring configuration class that implements the WebMvcConfigurer interface to customize Spring MVC's configuration. It registers a custom LoggingInterceptor that logs incoming requests. The addInterceptors() method adds the LoggingInterceptor to the application's interceptor registry, applying it to all incoming paths ("/**"). This configuration ensures that the LoggingInterceptor is invoked for every request, enabling logging for all HTTP requests processed by the Spring application.

12. **Product(Product Class):**
The Product class represents a book entity with attributes like bookTitle, bookAuthor, bookPrice, bookGenre, bookDiscount, and bookQuantity. It is mapped to a database table using JPA annotations (@Entity, @Id, @GeneratedValue, etc.). The class includes constructors, getters, setters, and a toString() method for convenient data handling. It is used in a bookstore system to manage books as products.

13. **ProductConfig(ProductConfig Class):**
The ProductConfig class is a Spring configuration class that initializes a set of sample products into the database when the application runs with the "product" profile active.

14. **ProductController(ProductController Class):**
The ProductController is a REST controller that exposes endpoints for managing products, including GET, POST, and PUT operations. It implements caching to retrieve products by ID efficiently and publishes product details to Kafka when a new product is created. The controller relies on the ProductService to handle the business logic for creating, fetching, and updating product data.

15. **ProductRepository(ProductRepository Interface):**
The ProductRepository interface extends JpaRepository to provide CRUD operations for the Product entity. It includes a custom query method findProductByBookTitle to find a product by its bookTitle. This repository is annotated with @Repository to indicate it's a Spring Data JPA repository responsible for accessing and interacting with the database.

16. **ProductService(ProductService Class):**
The ProductService class handles the business logic for managing products, including fetching, adding, and updating products. It interacts with the ProductRepository to retrieve and modify product data.

## Built With
Spring Boot (spring-boot-starter-web, spring-boot-starter-data-jpa, spring-boot-starter-cache)
PostgreSQL (postgresql)
Kafka (spring-kafka, spring-kafka-test)
Hazelcast (hazelcast-spring)
Lombok (lombok)
Testing (spring-boot-starter-test, h2)
SpringDoc OpenAPI (springdoc-openapi-starter-webmvc-ui)
Jakarta Servlet (jakarta.servlet-api)
Maven (spring-boot-maven-plugin, maven-compiler-plugin)

## Setup and Installation

Step 1: Clone the Project Repository:
    git clone https://github.com/luqmanul-hakim/java-technical-assessment.git

Step 2: Setup Postgresql:
    psql -U postgres
    CREATE DATABASE assessment;

Step 3: Set Up Kafka:
    Start Zookeeper: bin/zookeeper-server-start.sh config/zookeeper.properties
    Start Kafka: bin/kafka-server-start.sh config/server.properties
    Start Producer: 


Step 4: Configure application properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/assessment
    spring.datasource.username=postgres
    spring.datasource.password=
    spring.jpa.hibernate.ddl-auto=create-drop
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.properties.hibernate.format_sql=true
    spring.main.allow-bean-definition-overriding=true
    spring.profiles.active=customer,product
    server.error.include-message=always
    logging.level.root=INFO
    logging.level.org.springframework.web=DEBUG
    logging.level.com.technical.assessment=DEBUG
    logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n

    # Kafka Configuration
    spring.kafka.bootstrap-servers=localhost:9092
    spring.kafka.consumer.group-id=my-group
    spring.kafka.consumer.auto-offset-reset=earliest
    springdoc.api-docs.path=/api-docs
    springdoc.swagger-ui.path=/swagger-ui.html

Step 5: Build and Run the Application:
    mvn clean install

Step 6: Run the Application:
    mvn spring-boot:run

Step 7: Test Endpoint
    You can now test your application's REST endpoints. For example:
    Get all product:
        GET http://localhost:8080/api/v1/product

    Add new product:
        POST http://localhost:8080/api/v1/product
        Content-Type: application/json
        Body:
        {
            "bookTitle": "Silent Hill 4",
            "bookAuthor": "Luqman",
            "bookPrice": 10.00,
            "bookGenre": "horror",
            "bookDiscount": 5.00,
            "bookQuantity": 1
        }

Step 8: Verify Caching and Kafka Integration
    Kafka consumer: bin/kafka-console-consumer.sh --topic technical.assessment --bootstrap-server localhost:9092

Step 9: Additional Steps for Development:
    IDE Setup: If you are using an IDE like IntelliJ IDEA or Eclipse, you can import the project as a Maven project and run it directly from the IDE.
    Testing: Make sure to run unit tests to verify that everything works as expected.

## Contributors

**Muhammad Luqmanul Hakim Bin Abd Yazib**
**017-5414912**
