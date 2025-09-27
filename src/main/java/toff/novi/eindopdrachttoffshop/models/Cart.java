package toff.novi.eindopdrachttoffshop.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import toff.novi.eindopdrachttoffshop.exceptions.CartOperationException;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int cart_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Cart() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Cart(User user) {
        this();
        this.user = user;
    }

    public Integer getId() {
        return cart_id;
    }

    public void setId(Integer cart_id) {
        this.cart_id = cart_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getCartItems() {
        return orderItems.stream()
                .filter(OrderItem::isInCart)
                .collect(Collectors.toList());
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

    public void addOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            throw new CartOperationException("OrderItem mag niet null zijn");
        }
        if (orderItems.contains(orderItem)) {
            throw new CartOperationException("Dit item zit al in de winkelwagen");
        }
        orderItems.add(orderItem);
        orderItem.setCart(this);
        this.updatedAt = LocalDateTime.now();
    }

    public void removeOrderItem(OrderItem orderItem) {
        if (!orderItems.contains(orderItem)) {
            throw new CartOperationException("Dit item zit niet in de winkelwagen");
        }
        orderItems.remove(orderItem);
        orderItem.setCart(null);
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cart_id=" + cart_id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", itemCount=" + getCartItems().size() +
                '}';
    }
}
