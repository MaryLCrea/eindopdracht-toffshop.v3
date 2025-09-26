package toff.novi.eindopdrachttoffshop.services;

import org.springframework.stereotype.Service;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemResponseDto;
import toff.novi.eindopdrachttoffshop.enums.OrderAndItemStatus;
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
        var cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No cart found for user with id " + userId));

        return orderItemRepository.findByCartId(cart.getId())
                .stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());
    }

    public OrderItemResponseDto updateOrderItem(Integer userId, Integer orderItemId, OrderItemRequestDto dto) {
        var cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No cart found for user with id " + userId));

        OrderItem item = orderItemRepository.findByIdAndCart_Id(orderItemId, cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order item with id " + orderItemId + " not found in user's cart"));

        item.setQuantity(dto.getQuantity());

        if (dto.getStatus() != null) {
            validateStatusTransition(item.getStatus(), dto.getStatus());
            item.setStatus(dto.getStatus());
        }

        return new OrderItemResponseDto(orderItemRepository.save(item));
    }

    public void deleteOrderItem(Integer userId, Integer orderItemId) {
        var cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No cart found for user with id " + userId));

        OrderItem item = orderItemRepository.findByIdAndCart_Id(orderItemId, cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order item with id " + orderItemId + " not found in user's cart"));

        orderItemRepository.delete(item);
    }

    private void validateStatusTransition(OrderAndItemStatus currentStatus, OrderAndItemStatus newStatus) {
        if (currentStatus == OrderAndItemStatus.DELIVERED) {
            throw new IllegalArgumentException("Cannot change status of delivered items");
        }

        if (currentStatus == OrderAndItemStatus.CANCELLED_ORDER && newStatus != OrderAndItemStatus.CANCELLED_ORDER) {
            throw new IllegalArgumentException("Cannot change status of cancelled items");
        }
    }
}
