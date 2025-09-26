package toff.novi.eindopdrachttoffshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import toff.novi.eindopdrachttoffshop.dtos.ProductRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.ProductResponseDto;
import toff.novi.eindopdrachttoffshop.enums.*;
import toff.novi.eindopdrachttoffshop.enums.Brand;
import toff.novi.eindopdrachttoffshop.enums.Color;
import toff.novi.eindopdrachttoffshop.enums.Heel;
import toff.novi.eindopdrachttoffshop.enums.Size;
import toff.novi.eindopdrachttoffshop.models.Product;
import toff.novi.eindopdrachttoffshop.repositories.ProductRepository;
import toff.novi.eindopdrachttoffshop.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setProductName("Dansschoenen");
        testProduct.setDescription("Standaard dansschoenen");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setCategory(Category.FASHION);
        testProduct.setBrand(Brand.PORTDANCE);
        testProduct.setColor(Color.BLACK);
        testProduct.setHeel(Heel.FLAT);
        testProduct.setSize(Size.SIZE_37);
        testProduct.setStockQuantity(10);
        testProduct.setIsActive(true);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        ProductRequestDto dto = new ProductRequestDto();
        dto.setProductName("Dansschoenen");
        dto.setDescription("Standaard dansschoenen");
        dto.setPrice(new BigDecimal("99.99"));
        dto.setCategory(Category.FASHION);
        dto.setBrand(Brand.PORTDANCE);
        dto.setColor(Color.BLACK);
        dto.setHeel(Heel.FLAT);
        dto.setSize(Size.SIZE_39);
        dto.setStockQuantity(10);
        dto.setIsActive(true);

        ProductResponseDto result = productService.createProduct(dto);

        assertThat(result.getName()).isEqualTo("Dansschoenen");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetProductById_WhenExists() {
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));

        ProductResponseDto result = productService.getProductById(1);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Dansschoenen");
    }

    @Test
    void testGetProductById_WhenNotExists() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1));
    }

    @Test
    void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(testProduct);
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductResponseDto> results = productService.getAllProducts();

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Dansschoenen");
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        ProductRequestDto updateRequest = new ProductRequestDto();
        updateRequest.setProductName("Dansschoenen Ultra");
        updateRequest.setDescription("Verbeterde dansschoenen");
        updateRequest.setPrice(new BigDecimal("129.99"));
        updateRequest.setCategory(testProduct.getCategory());
        updateRequest.setBrand(testProduct.getBrand());
        updateRequest.setColor(testProduct.getColor());
        updateRequest.setHeel(testProduct.getHeel());
        updateRequest.setSize(testProduct.getSize());
        updateRequest.setStockQuantity(testProduct.getStockQuantity());
        updateRequest.setIsActive(testProduct.getIsActive());

        ProductResponseDto result = productService.updateProduct(1, updateRequest);

        assertThat(result.getName()).isEqualTo("Dansschoenen Ultra");
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("129.99"));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_WhenExists() {
        when(productRepository.existsById(1)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1);

        productService.deleteProduct(1);

        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteProduct_WhenNotExists() {
        when(productRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1));
    }
}
