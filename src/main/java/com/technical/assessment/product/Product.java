package com.technical.assessment.product;

import jakarta.persistence.*;

@Entity
@Table
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    private String bookTitle;
    private String bookAuthor;
    private Double bookPrice;
    private String bookGenre;
    private Double bookDiscount;
    private Integer bookQuantity;

    public Product() {
    }

    public Product(Long id,
                    String bookTitle,
                    String bookAuthor,
                    Double bookPrice,
                    String bookGenre,
                    Double bookDiscount,
                    Integer bookQuantity){
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.bookGenre = bookGenre;
        this.bookDiscount = bookDiscount;
        this.bookQuantity = bookQuantity;
    }

    public Product(String bookTitle,
                    String bookAuthor,
                    Double bookPrice,
                    String bookGenre,
                    Double bookDiscount,
                    Integer bookQuantity) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.bookGenre = bookGenre;
        this.bookDiscount = bookDiscount;
        this.bookQuantity = bookQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor= bookAuthor;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public Double getBookDiscount() {
        return bookDiscount;
    }

    public void setBookDiscount(Double bookDiscount) {
        this.bookDiscount = bookDiscount;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPrice='" + bookPrice +
                ", bookGenre='" + bookGenre + '\'' +
                ", bookDiscount=" + bookDiscount +
                ", bookQuantity=" + bookQuantity +
                '}';
    }
}