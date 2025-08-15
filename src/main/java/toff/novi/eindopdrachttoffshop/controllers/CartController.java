package toff.novi.eindopdrachttoffshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.CartResponseDto;
import toff.novi.eindopdrachttoffshop.services.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/user/{userId}/items")
    public ResponseEntity<CartResponseDto> addItemToCart(@PathVariable Integer userId,
                                                         @Valid
                                                         @RequestBody OrderItemRequestDto orderItemRequestDto) {
        CartResponseDto updatedCart = cartService.addItemToCart(userId, orderItemRequestDto);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDto> getCartByUserId(@PathVariable Integer userId) {
        CartResponseDto cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/user/{userId}/items/{orderItemId}")
    public ResponseEntity<CartResponseDto> updateOrderItemQuantity(@PathVariable Integer userId,
                                                                   @PathVariable Integer orderItemId,
                                                                   @RequestParam Integer quantity) {
        CartResponseDto updatedCart = cartService.updateOrderItemQuantity(userId, orderItemId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/user/{userId}/items/{orderItemId}")
    public ResponseEntity<CartResponseDto> removeItemFromCart(@PathVariable Integer userId,
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