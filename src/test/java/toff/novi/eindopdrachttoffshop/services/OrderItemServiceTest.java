package toff.novi.eindopdrachttoffshop.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemResponseDto;
import toff.novi.eindopdrachttoffshop.models.OrderItem;
import toff.novi.eindopdrachttoffshop.repositories.CartRepository;
import toff.novi.eindopdrachttoffshop.repositories.OrderItemRepository;

import java.util.Optional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @Mock
    OrderItemRepository orderItemRepository;

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    OrderItemService orderItemService;

    @Test
    @DisplayName("Should return correct orderItem by id")
    void shouldReturnCorrectOrderItemById() {

        OrderItem orderItem = new OrderItem("Yoga Mat Premium", new BigDecimal("45.99"), 30);
        orderItem.setId(1);

        when(orderItemRepository.findById(1)).thenReturn(Optional.of(orderItem));

        OrderItemResponseDto orderItemResponseDto = orderItemService.getOrderItemById(1);

        assertEquals("Yoga Mat Premium", orderItemResponseDto.getProductName());
        assertEquals(new BigDecimal("45.99"), orderItemResponseDto.getProductPrice());
        assertEquals(30, orderItemResponseDto.getQuantity());
    }
}