package toff.novi.eindopdrachttoffshop.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void shouldKeepProductNameAndSkuAndPriceAndQuantity() {

        // Maak een OrderItem aan met de nieuwe constructor
        OrderItem orderItem = new OrderItem(
                "Ray Rose 825 Ballroom",  // productName
                "RR825",                  // sku
                new BigDecimal("149.99"), // productPrice
                1                         // quantity
        );

        // Controleer alle velden
        assertEquals("Ray Rose 825 Ballroom", orderItem.getProductName());
        assertEquals("RR825", orderItem.getSku());
        assertEquals(new BigDecimal("149.99"), orderItem.getProductPrice());
        assertEquals(1, orderItem.getQuantity());
        assertEquals(orderItem.getStatus().IN_CART, orderItem.getStatus()); // controleer standaard status
    }
}
