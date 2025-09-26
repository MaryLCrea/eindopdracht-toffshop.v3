//package toff.novi.eindopdrachttoffshop.models;
//
//import jakarta.persistence.*;
//import toff.novi.eindopdrachttoffshop.enums.OrderAndItemStatus;
//
//import java.math.BigDecimal;
//
//@Entity
//@Table(name = "order_items")
//public class OrderItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
//
//    @Column(name = "product_name", nullable = false)
//    private String productName;
//
//    @Column(name = "product_price", nullable = false, precision = 10, scale = 2)
//    private BigDecimal productPrice;
//
//    @Column(nullable = false)
//    private Integer quantity;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private OrderAndItemStatus status = OrderAndItemStatus.IN_CART;
//
//    public OrderItem() {
//    }
//
//    public OrderItem(String productName, BigDecimal productPrice, Integer quantity) {
//        this.productName = productName;
//        this.productPrice = productPrice;
//        this.quantity = quantity;
//        this.status = OrderAndItemStatus.IN_CART;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
//
//    public Cart getCart() {
//        return cart;
//    }
//
//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }
//
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public BigDecimal getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(BigDecimal productPrice) {
//        this.productPrice = productPrice;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public OrderAndItemStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(OrderAndItemStatus status) {
//        this.status = status;
//    }
//
//    public BigDecimal getTotalPrice() {
//        return productPrice.multiply(BigDecimal.valueOf(quantity));
//    }
//
//    public boolean isInCart() {
//        return status.isInCart();
//    }
//
//    public boolean isOrdered() {
//        return status.isOrdered();
//    }
//
//    @Override
//    public String toString() {
//        return "OrderItem{" +
//                "id=" + id +
//                ", productName='" + productName + '\'' +
//                ", quantity=" + quantity +
//                ", price=" + productPrice +
//                ", status=" + status +
//                '}';
//    }
//}

package toff.novi.eindopdrachttoffshop.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import toff.novi.eindopdrachttoffshop.enums.OrderAndItemStatus;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderAndItemStatus status;

    public OrderItem() {
        this.status = OrderAndItemStatus.IN_CART;
    }

    public OrderItem(String productName, String sku, BigDecimal productPrice, Integer quantity) {
        this.productName = productName;
        this.sku = sku;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.status = OrderAndItemStatus.IN_CART;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public OrderAndItemStatus getStatus() { return status; }
    public void setStatus(OrderAndItemStatus status) { this.status = status; }

    public boolean isInCart() {
        return status == OrderAndItemStatus.IN_CART || status == OrderAndItemStatus.RETURNED_CART;
    }

    public BigDecimal getTotalPrice() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}




