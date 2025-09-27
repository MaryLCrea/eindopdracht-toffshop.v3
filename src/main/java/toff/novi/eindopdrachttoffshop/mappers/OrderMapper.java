package toff.novi.eindopdrachttoffshop.mappers;

import toff.novi.eindopdrachttoffshop.dtos.OrderItemResponseDto;
import toff.novi.eindopdrachttoffshop.dtos.OrderResponseDto;
import toff.novi.eindopdrachttoffshop.models.Order;
import toff.novi.eindopdrachttoffshop.models.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDto toResponseDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setStatus(order.getStatusOrder());

        if (order.getUser() != null) {
            dto.setUserName(order.getUser().getName());
        }

        List<OrderItemResponseDto> itemDtos = order.getItems()
                .stream()
                .map(OrderMapper::mapOrderItem)
                .collect(Collectors.toList());
        dto.setItems(itemDtos);

        dto.setTotalPrice(order.getTotalPrice());

        return dto;
    }

    private static OrderItemResponseDto mapOrderItem(OrderItem item) {
        if (item == null) {
            return null;
        }

        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setProductPrice(item.getProductPrice());
        dto.setStatus(item.getStatus());

        dto.setProductName(item.getProductName());

        return dto;
    }
}



