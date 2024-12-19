package com.technical.assessment.product;

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
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        log.debug("Retrieving all products from the database");
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Product with ID: " + id + " not found"
                ));
    }

    public void addNewProduct(Product product) {
        Optional<Product> productOptional = productRepository
                .findProductByBookTitle(product.getBookTitle());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("This book title already taken");
        }
        log.info("Adding a new book with title: {}", product);
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(
            Long productId,
            String bookTitle,
            String bookAuthor,
            Double bookPrice,
            String bookGenre,
            Double bookDiscount,
            Integer bookQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException(
                        "product with id " + productId + " does not exist"));

        if (bookTitle != null &&
                !bookTitle.isEmpty() && !Objects.equals(product.getBookTitle(), bookTitle)) {
            Optional<Product> productOptional = productRepository
                    .findProductByBookTitle(bookTitle);
            if (productOptional.isPresent()) {
                throw new IllegalStateException("Book Title already taken");
            }
            product.setBookTitle(bookTitle);
        }

        if (bookAuthor != null &&
                !bookAuthor.isEmpty() && !Objects.equals(product.getBookAuthor(), bookTitle)) {
            product.setBookAuthor(bookAuthor);
        }

        if (bookPrice != null &&
                !bookPrice.isNaN() &&
                !Objects.equals(product.getBookPrice(), bookPrice)) {
            product.setBookPrice(bookPrice);
        }

        if (bookGenre != null &&
                !bookGenre.isEmpty() && !Objects.equals(product.getBookGenre(), bookGenre)) {
            product.setBookGenre(bookGenre);
        }

        if (bookDiscount != null &&
                !bookDiscount.isNaN() &&
                !Objects.equals(product.getBookDiscount(), bookDiscount)) {
            product.setBookDiscount(bookDiscount);
        }

        if (bookQuantity != null &&
                !Objects.equals(product.getBookQuantity(), bookQuantity)) {
            product.setBookQuantity(bookQuantity);
        }

    }
}
