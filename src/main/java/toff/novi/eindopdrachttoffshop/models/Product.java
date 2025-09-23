package toff.novi.eindopdrachttoffshop.models;

import jakarta.persistence.*;
import toff.novi.eindopdrachttoffshop.enums.*;
import toff.novi.eindopdrachttoffshop.enums.Brand;
import toff.novi.eindopdrachttoffshop.enums.Color;
import toff.novi.eindopdrachttoffshop.enums.Heel;
import toff.novi.eindopdrachttoffshop.enums.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 600)
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Heel heel;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Column(nullable = false)
    private Integer stockQuantity = 0;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "image_name", length = 255)
    private String imageName;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    public Product() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Product(String name, String description, BigDecimal price, Category category) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Product(String name, String description, BigDecimal price, Brand brand,
                   Color color, Heel heel, Size size, Integer stockQuantity) {
        this(name, description, price, Category.FASHION);
        this.brand = brand;
        this.color = color;
        this.heel = heel;
        this.size = size;
        this.stockQuantity = stockQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Heel getHeel() {
        return heel;
    }

    public void setHeel(Heel heel) {
        this.heel = heel;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDanceShoe() {
        return category == Category.FASHION && brand != null;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void reduceStock(Integer quantity) {
        if (stockQuantity >= quantity) {
            this.stockQuantity -= quantity;
            this.updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Insufficient stock. Available: " + stockQuantity + ", Requested: " + quantity);
        }
    }

    public void increaseStock(Integer quantity) {
        this.stockQuantity += quantity;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", brand=" + brand +
                ", price=" + price +
                ", stock=" + stockQuantity +
                '}';
    }
}