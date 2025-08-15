package toff.novi.eindopdrachttoffshop.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class OrderItemRequestDto {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be positive")
    private BigDecimal productPrice;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    public OrderItemRequestDto() {}

    public OrderItemRequestDto(String productName, BigDecimal productPrice, Integer quantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}