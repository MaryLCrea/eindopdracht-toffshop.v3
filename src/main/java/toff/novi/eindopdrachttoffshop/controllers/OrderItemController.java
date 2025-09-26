package toff.novi.eindopdrachttoffshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemResponseDto;
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
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderItemResponseDto>> getOrderItemsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderItemService.getOrderItemsByUserId(userId));
    }

    @PutMapping("/user/{userId}/items/{orderItemId}")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(
            @PathVariable Integer userId,
            @PathVariable Integer orderItemId,
            @RequestBody OrderItemRequestDto requestDto) {

        return ResponseEntity.ok(orderItemService.updateOrderItem(userId, orderItemId, requestDto));
    }

    @DeleteMapping("/user/{userId}/items/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItem(
            @PathVariable Integer userId,
            @PathVariable Integer orderItemId) {

        orderItemService.deleteOrderItem(userId, orderItemId);
        return ResponseEntity.noContent().build();
    }
}
