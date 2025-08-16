package toff.novi.eindopdrachttoffshop.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import toff.novi.eindopdrachttoffshop.enums.*;
import java.math.BigDecimal;

public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name may contain up to 255 characters")
    private String name;

    @Size(max = 600, message = "Description may contain up to 600 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Category is required")
    private Category category;

    private Brand brand;
    private Color color;
    private Heel heel;
    private Size size;

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity must be zero or positive")
    private Integer stockQuantity;

    private Boolean isActive = true;

    public ProductRequestDto() {}

    public ProductRequestDto(String name, String description, BigDecimal price, Category category, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
    }

    public ProductRequestDto(String name, String description, BigDecimal price,
                             Brand brand, Color color, Heel heel, Size size, Integer stockQuantity) {
        this(name, description, price, Category.FASHION, stockQuantity);
        this.brand = brand;
        this.color = color;
        this.heel = heel;
        this.size = size;
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
}