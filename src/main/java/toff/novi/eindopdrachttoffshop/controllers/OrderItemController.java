package toff.novi.eindopdrachttoffshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemResponseDto;
import toff.novi.eindopdrachttoffshop.enums.OrderItemStatus;
import toff.novi.eindopdrachttoffshop.services.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDto> getOrderItemById(@PathVariable Integer id) {
        OrderItemResponseDto orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItem);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderItemResponseDto>> getOrderItemsByUserId(@PathVariable Integer userId) {
        List<OrderItemResponseDto> orderItems = orderItemService.getOrderItemsByUserId(userId);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<OrderItemResponseDto>> getOrderItemsByUserIdAndStatus(
            @PathVariable Integer userId,
            @PathVariable OrderItemStatus status) {
        List<OrderItemResponseDto> orderItems = orderItemService.getOrderItemsByUserIdAndStatus(userId, status);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderItemResponseDto>> getOrderItemsByStatus(@PathVariable OrderItemStatus status) {
        List<OrderItemResponseDto> orderItems = orderItemService.getOrderItemsByStatus(status);
        return ResponseEntity.ok(orderItems);
    }
}