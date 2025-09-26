package toff.novi.eindopdrachttoffshop.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.CartResponseDto;
import toff.novi.eindopdrachttoffshop.enums.OrderAndItemStatus;
import toff.novi.eindopdrachttoffshop.exceptions.ResourceNotFoundException;
import toff.novi.eindopdrachttoffshop.exceptions.InvalidOrderItemStatusTransitionException;
import toff.novi.eindopdrachttoffshop.models.Cart;
import toff.novi.eindopdrachttoffshop.models.OrderItem;
import toff.novi.eindopdrachttoffshop.models.User;
import toff.novi.eindopdrachttoffshop.repositories.CartRepository;
import toff.novi.eindopdrachttoffshop.repositories.OrderItemRepository;
import toff.novi.eindopdrachttoffshop.repositories.UserRepository;
import toff.novi.eindopdrachttoffshop.validators.ProductValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final ProductValidator productValidator;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       OrderItemRepository orderItemRepository,
                       UserService userService,
                       ProductValidator productValidator,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        this.productValidator = productValidator;
        this.userRepository = userRepository;
    }

    public CartResponseDto createCartForUser(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

            cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(LocalDateTime.now());
            cart.setUpdatedAt(LocalDateTime.now());
            cart = cartRepository.save(cart);
        }

        return new CartResponseDto(cart);
    }

    public CartResponseDto getCartByUserId(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userService.getSingleUser(userId);
                    return cartRepository.save(new Cart(user));
                });
        return new CartResponseDto(cart);
    }

    public CartResponseDto addItemsToCart(Integer userId, List<OrderItemRequestDto> orderItems) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userService.getSingleUser(userId);
                    return cartRepository.save(new Cart(user));
                });

        for (OrderItemRequestDto itemRequest : orderItems) {
            productValidator.validate(itemRequest);

            Optional<OrderItem> existingItem = orderItemRepository
                    .findByCartIdAndProductNameAndStatus(cart.getId(), itemRequest.getProductName(), OrderAndItemStatus.IN_CART);

            if (existingItem.isPresent()) {
                OrderItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + itemRequest.getQuantity());
                orderItemRepository.save(item);
            } else {
                OrderItem newItem = new OrderItem(
                        itemRequest.getProductName(),
                        itemRequest.getSku(),
                        itemRequest.getProductPrice(),
                        itemRequest.getQuantity()
                );
                cart.addOrderItem(newItem);
                orderItemRepository.save(newItem);
            }
        }

        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);
        return new CartResponseDto(updatedCart);
    }

    public CartResponseDto updateOrderItem(Integer userId, Integer orderItemId, OrderItemRequestDto itemRequest) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with id " + userId));

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item with id " + orderItemId + " not found"));

        if (!orderItem.getCart().getId().equals(cart.getId()) || !orderItem.isInCart()) {
            throw new IllegalArgumentException("Order item does not belong to this user's cart or is not in cart status");
        }

        productValidator.validate(itemRequest);

        orderItem.setProductName(itemRequest.getProductName());
        orderItem.setSku(itemRequest.getSku());
        orderItem.setProductPrice(itemRequest.getProductPrice());
        orderItem.setQuantity(itemRequest.getQuantity());

        OrderAndItemStatus oldStatus = orderItem.getStatus();
        OrderAndItemStatus newStatus = itemRequest.getStatus();

        if (oldStatus == OrderAndItemStatus.SHIPPED && newStatus != OrderAndItemStatus.SHIPPED) {
            throw new InvalidOrderItemStatusTransitionException(
                    "Cannot change order item from SHIPPED to " + newStatus
            );
        }

        orderItem.setStatus(newStatus);

        orderItemRepository.save(orderItem);

        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);

        return new CartResponseDto(updatedCart);
    }

    public CartResponseDto removeItemFromCart(Integer userId, Integer orderItemId) {
        User currentUser = getCurrentUser();
        checkAccess(userId, currentUser);

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
        User currentUser = getCurrentUser();
        checkAccess(userId, currentUser);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with id " + userId));

        cart.getCartItems().forEach(item -> {
            cart.removeOrderItem(item);
            orderItemRepository.delete(item);
        });

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    private void checkAccess(Integer userId, User currentUser) {
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRolename().equals("ROLE_ADMIN"));

        if (!isAdmin && !currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("Je hebt geen toestemming om deze actie uit te voeren");
        }
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username);
    }
}