package toff.novi.eindopdrachttoffshop.dtos;

import toff.novi.eindopdrachttoffshop.models.Product;
import toff.novi.eindopdrachttoffshop.enums.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponseDto {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;
    private Brand brand;
    private Color color;
    private Heel heel;
    private Size size;
    private Integer stockQuantity;
    private Boolean isActive;
    private Boolean inStock;
    private Boolean isDanceShoe;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponseDto() {}

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.brand = product.getBrand();
        this.color = product.getColor();
        this.heel = product.getHeel();
        this.size = product.getSize();
        this.stockQuantity = product.getStockQuantity();
        this.isActive = product.getIsActive();
        this.inStock = product.isInStock();
        this.isDanceShoe = product.isDanceShoe();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
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

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Boolean getIsDanceShoe() {
        return isDanceShoe;
    }

    public void setIsDanceShoe(Boolean isDanceShoe) {
        this.isDanceShoe = isDanceShoe;
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
}