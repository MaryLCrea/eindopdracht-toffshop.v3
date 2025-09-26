package toff.novi.eindopdrachttoffshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import toff.novi.eindopdrachttoffshop.dtos.CartRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.CartResponseDto;
import toff.novi.eindopdrachttoffshop.services.CartService;
import toff.novi.eindopdrachttoffshop.services.UserService;
import toff.novi.eindopdrachttoffshop.security.MyUserDetails;
import toff.novi.eindopdrachttoffshop.models.User;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CartResponseDto> createCart(@Valid @RequestBody CartRequestDto cartRequestDto) {
        CartResponseDto cart = cartService.createCartForUser(cartRequestDto.getUserId());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/me/items")
    public ResponseEntity<CartResponseDto> addItemsToMyCart(
            @AuthenticationPrincipal MyUserDetails principal,
            @Valid @RequestBody List<OrderItemRequestDto> orderItems) {

        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        User currentUser = userService.getUserByEmail(principal.getUsername());
        CartResponseDto updatedCart = cartService.addItemsToCart(currentUser.getId(), orderItems);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDto> getCartByUserId(@PathVariable Integer userId) {
        CartResponseDto cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/user/{userId}/items/{orderItemId}")
    public ResponseEntity<CartResponseDto> updateOrderItem(
            @PathVariable Integer userId,
            @PathVariable Integer orderItemId,
            @RequestBody OrderItemRequestDto orderItemRequestDto) {

        CartResponseDto updatedCart = cartService.updateOrderItem(userId, orderItemId, orderItemRequestDto);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/user/{userId}/items/{orderItemId}")
    public ResponseEntity<CartResponseDto> removeItemFromCart(
            @PathVariable Integer userId,
            @PathVariable Integer orderItemId) {

        CartResponseDto updatedCart = cartService.removeItemFromCart(userId, orderItemId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Integer userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
