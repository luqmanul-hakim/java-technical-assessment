package com.technical.assessment.product;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
@Slf4j
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProductController(ProductService productService, KafkaTemplate<String, String> kafkaTemplate){
        this.productService = productService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping
    public List<Product> getProducts() {
        log.info("Fetching all products");
        return productService.getProducts();
    }

    @Cacheable(value = "products", key = "#productId")
    @GetMapping("{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    public void registerNewProduct(@RequestBody Product product){
        log.info("Registering a new product: {}", product);
        productService.addNewProduct(product);

        //publish kafka
        kafkaTemplate.send("technical.assessment", product.toString());

    }

    @CachePut(value = "products", key="#productId")
    @PutMapping(path="{productId}")
    public void updateProduct(
            @PathVariable("productId") Long productId,
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) String bookAuthor,
            @RequestParam(required = false) Double bookPrice,
            @RequestParam(required = false) String bookGenre,
            @RequestParam(required = false) Double bookDiscount,
            @RequestParam(required = false) Integer bookQuantity){
        log.info("Updating Product: {}", productId);
        productService.updateProduct(productId, bookTitle, bookAuthor, bookPrice, bookGenre, bookDiscount, bookQuantity);
    }
}
