package toff.novi.eindopdrachttoffshop.services;

import org.springframework.stereotype.Service;
import toff.novi.eindopdrachttoffshop.dtos.ProductRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.ProductResponseDto;
import toff.novi.eindopdrachttoffshop.enums.*;
import toff.novi.eindopdrachttoffshop.exceptions.ResourceNotFoundException;
import toff.novi.eindopdrachttoffshop.models.Cart;
import toff.novi.eindopdrachttoffshop.models.OrderItem;
import toff.novi.eindopdrachttoffshop.models.Product;
import toff.novi.eindopdrachttoffshop.models.User;
import toff.novi.eindopdrachttoffshop.repositories.CartRepository;
import toff.novi.eindopdrachttoffshop.repositories.ProductRepository;
import toff.novi.eindopdrachttoffshop.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository,
                          CartRepository cartRepository,
                          UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setBrand(dto.getBrand());
        product.setColor(dto.getColor());
        product.setHeel(dto.getHeel());
        product.setSize(dto.getSize());
        product.setStockQuantity(dto.getStockQuantity());
        product.setIsActive(dto.getIsActive());

        Product saved = productRepository.save(product);
        return new ProductResponseDto(saved);
    }

    public ProductResponseDto getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> getActiveProducts() {
        return productRepository.findByIsActiveTrue().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> getProductsByCategory(Category category) {
        return productRepository.findByIsActiveTrueAndCategory(category).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> getProductsByBrand(Brand brand) {
        return productRepository.findByIsActiveTrueAndBrand(brand).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> searchProductsByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> getDanceShoesByBrandColorAndSize(Brand brand, Color color, Size size) {
        return productRepository.findByMultipleCriteria(brand, color, size, null).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public ProductResponseDto updateProduct(Integer id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setBrand(dto.getBrand());
        product.setColor(dto.getColor());
        product.setHeel(dto.getHeel());
        product.setSize(dto.getSize());
        product.setStockQuantity(dto.getStockQuantity());
        product.setIsActive(dto.getIsActive());
        product.setUpdatedAt(LocalDateTime.now());

        return new ProductResponseDto(productRepository.save(product));
    }

    public ProductResponseDto updateStock(Integer id, Integer stock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        product.setStockQuantity(stock);
        product.setUpdatedAt(LocalDateTime.now());
        return new ProductResponseDto(productRepository.save(product));
    }

    public ProductResponseDto deactivateProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        product.setIsActive(false);
        product.setUpdatedAt(LocalDateTime.now());
        return new ProductResponseDto(productRepository.save(product));
    }

    public ProductResponseDto updateProductImage(Integer id, String imageName, String imageUrl) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        product.setImageName(imageName);
        product.setImageUrl(imageUrl);
        product.setUpdatedAt(LocalDateTime.now());
        return new ProductResponseDto(productRepository.save(product));
    }

    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    public OrderItem addProductToCart(Integer userId, Integer productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .filter(Product::getIsActive)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product met id " + productId + " bestaat niet of is niet actief"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User met id " + userId + " niet gevonden"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        OrderItem item = new OrderItem();
        item.setProductName(product.getProductName());
        item.setProductPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setStatus(OrderAndItemStatus.IN_CART);

        cart.addOrderItem(item);
        cartRepository.save(cart);

        return item;
    }
}
