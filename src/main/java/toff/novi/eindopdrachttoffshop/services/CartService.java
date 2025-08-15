package toff.novi.eindopdrachttoffshop.services;

import org.springframework.stereotype.Service;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.CartResponseDto;
import toff.novi.eindopdrachttoffshop.enums.OrderItemStatus;
import toff.novi.eindopdrachttoffshop.exceptions.ResourceNotFoundException;
import toff.novi.eindopdrachttoffshop.models.Cart;
import toff.novi.eindopdrachttoffshop.models.OrderItem;
import toff.novi.eindopdrachttoffshop.models.User;
import toff.novi.eindopdrachttoffshop.repositories.CartRepository;
import toff.novi.eindopdrachttoffshop.repositories.OrderItemRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;

    public CartService(CartRepository cartRepository, OrderItemRepository orderItemRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
    }

    public CartResponseDto getCartByUserId(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userService.getSingleUser(userId);
                    return cartRepository.save(new Cart(user));
                });
        return new CartResponseDto(cart);
    }

    public CartResponseDto addItemToCart(Integer userId, OrderItemRequestDto orderItemRequestDto) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userService.getSingleUser(userId);
                    return cartRepository.save(new Cart(user));
                });

        Optional<OrderItem> existingItem = orderItemRepository
                .findByCartIdAndProductNameAndStatus(cart.getId(), orderItemRequestDto.getProductName(), OrderItemStatus.IN_CART);

        if (existingItem.isPresent()) {
            OrderItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + orderItemRequestDto.getQuantity());
            orderItemRepository.save(item);
        } else {
            OrderItem newItem = new OrderItem(
                    orderItemRequestDto.getProductName(),
                    orderItemRequestDto.getProductPrice(),
                    orderItemRequestDto.getQuantity()
            );
            cart.addOrderItem(newItem);
            orderItemRepository.save(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);
        return new CartResponseDto(updatedCart);
    }

    public CartResponseDto updateOrderItemQuantity(Integer userId, Integer orderItemId, Integer newQuantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with id " + userId));

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item with id " + orderItemId + " not found"));

        if (!orderItem.getCart().getId().equals(cart.getId()) || !orderItem.isInCart()) {
            throw new IllegalArgumentException("Order item does not belong to this user's cart or is not in cart status");
        }

        if (newQuantity <= 0) {
            cart.removeOrderItem(orderItem);
            orderItemRepository.delete(orderItem);
        } else {
            orderItem.setQuantity(newQuantity);
            orderItemRepository.save(orderItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);
        return new CartResponseDto(updatedCart);
    }

    public CartResponseDto removeItemFromCart(Integer userId, Integer orderItemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with id " + userId));

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item with id " + orderItemId + " not found"));

        if (!orderItem.getCart().getId().equals(cart.getId()) || !orderItem.isInCart()) {
            throw new IllegalArgumentException("Order item does not belong to this user's cart or is not in cart status");
        }

        cart.removeOrderItem(orderItem);
        orderItemRepository.delete(orderItem);

        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);
        return new CartResponseDto(updatedCart);
    }

    public void clearCart(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with id " + userId));

        cart.getCartItems().forEach(item -> {
            cart.removeOrderItem(item);
            orderItemRepository.delete(item);
        });

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }
}