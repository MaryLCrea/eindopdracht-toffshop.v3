package toff.novi.eindopdrachttoffshop.validators;

import org.springframework.stereotype.Component;
import toff.novi.eindopdrachttoffshop.dtos.OrderItemRequestDto;
import toff.novi.eindopdrachttoffshop.models.Product;
import toff.novi.eindopdrachttoffshop.repositories.ProductRepository;
import toff.novi.eindopdrachttoffshop.exceptions.ProductValidationException;

@Component
public class ProductValidator {

    private final ProductRepository productRepository;

    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validate(OrderItemRequestDto orderItemDto) {
        Product product = productRepository.findBySku(orderItemDto.getSku())
                .orElseThrow(() -> ProductValidationException.notFound(orderItemDto.getSku()));

        if (product.getPrice().compareTo(orderItemDto.getProductPrice()) != 0) {
            throw ProductValidationException.priceMismatch(orderItemDto.getSku());
        }

        if (!product.getProductName().equals(orderItemDto.getProductName())) {
            throw ProductValidationException.nameMismatch(orderItemDto.getSku());
        }

        if (!product.getSku().matches("\\d{4}")) {
            throw new ProductValidationException(
                    "SKU " + product.getSku() + " is ongeldig. SKU moet uit 4 cijfers bestaan."
            );
        }
    }
}
