package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String brand;
    private Integer stock;
    private Double price;

    @Column(name = "discount_type")
    private String discountType;

    // 1:1 Owner - สร้าง detail_id FK ในตาราง products
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    private ProductDetail detail;

    // 1:N One Side - mappedBy อ้างถึงฟิลด์ 'product' ใน Review
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Product() {}

    // Helper methods สำหรับจัดการความสัมพันธ์สองทาง
    public void addReview(Review review) {
        reviews.add(review);
        review.setProduct(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setProduct(null);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public ProductDetail getDetail() { return detail; }
    public void setDetail(ProductDetail detail) { 
        this.detail = detail;
        if (detail != null) {
            detail.setProduct(this);
        }
    }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public Double getDiscountedPrice() {
    if (this.price == null) {
        return 0.0;
    }
    if ("MEMBER".equalsIgnoreCase(this.discountType)) {
        return this.price * 0.90; // 10% discount
    } else if ("SEASONAL".equalsIgnoreCase(this.discountType)) {
        return this.price * 0.80; // 20% discount
    }
    return this.price; // No discount
}
}