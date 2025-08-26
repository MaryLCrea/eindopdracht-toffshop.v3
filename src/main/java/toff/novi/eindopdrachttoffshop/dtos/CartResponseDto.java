package toff.novi.eindopdrachttoffshop.dtos;

import toff.novi.eindopdrachttoffshop.models.Cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CartResponseDto {

    private Integer id;
    private Integer userId;
    private String userName;
    private List<OrderItemResponseDto> cartItems;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CartResponseDto() {
    }

    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.userName = cart.getUser().getName();
        this.cartItems = cart.getCartItems().stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());
        this.totalAmount = calculateTotalAmount();
        this.createdAt = cart.getCreatedAt();
        this.updatedAt = cart.getUpdatedAt();
    }

    private BigDecimal calculateTotalAmount() {
        return cartItems.stream()
                .map(OrderItemResponseDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<OrderItemResponseDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<OrderItemResponseDto> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
