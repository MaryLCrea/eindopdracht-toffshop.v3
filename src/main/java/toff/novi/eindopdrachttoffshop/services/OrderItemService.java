package toff.novi.eindopdrachttoffshop.services;

import org.springframework.stereotype.Service;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemResponseDto;
import toff.novi.eindopdrachttoffshop.enums.OrderItemStatus;
import toff.novi.eindopdrachttoffshop.exceptions.ResourceNotFoundException;
import toff.novi.eindopdrachttoffshop.models.OrderItem;
import toff.novi.eindopdrachttoffshop.repositories.CartRepository;
import toff.novi.eindopdrachttoffshop.repositories.OrderItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, CartRepository cartRepository) {
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
    }

    public OrderItemResponseDto getOrderItemById(Integer id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item with id " + id + " not found"));
        return new OrderItemResponseDto(orderItem);
    }

    public List<OrderItemResponseDto> getOrderItemsByUserId(Integer userId) {

        return cartRepository.findByUserId(userId)
                .map(cart -> orderItemRepository.findByCartId(cart.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("No cart found for user with id " + userId))
                .stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<OrderItemResponseDto> getOrderItemsByUserIdAndStatus(Integer userId, OrderItemStatus status) {
        return cartRepository.findByUserId(userId)
                .map(cart -> orderItemRepository.findByCartIdAndStatus(cart.getId(), status))
                .orElseThrow(() -> new ResourceNotFoundException("No cart found for user with id " + userId))
                .stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<OrderItemResponseDto> getOrderItemsByStatus(OrderItemStatus status) {
        return orderItemRepository.findByStatus(status)
                .stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<OrderItemResponseDto> getOrderItemsByOrderId(Integer orderId) {
        return orderItemRepository.findByOrder_Id(orderId)
                .stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());
    }

    public OrderItemResponseDto updateOrderItemStatus(Integer id, OrderItemStatus newStatus) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item with id " + id + " not found"));

        OrderItemStatus currentStatus = orderItem.getStatus();

        validateStatusTransition(currentStatus, newStatus);

        orderItem.setStatus(newStatus);
        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);

        return new OrderItemResponseDto(updatedOrderItem);
    }

    private void validateStatusTransition(OrderItemStatus currentStatus, OrderItemStatus newStatus) {

        if (currentStatus == OrderItemStatus.DELIVERED) {
            throw new IllegalArgumentException("Cannot change status of delivered items");
        }

        if (currentStatus == OrderItemStatus.CANCELLED && newStatus != OrderItemStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot change status of cancelled items");
        }
    }
}
