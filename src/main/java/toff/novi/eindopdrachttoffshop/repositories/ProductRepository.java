package toff.novi.eindopdrachttoffshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toff.novi.eindopdrachttoffshop.models.Product;
import toff.novi.eindopdrachttoffshop.enums.Size;
import toff.novi.eindopdrachttoffshop.enums.Color;
import toff.novi.eindopdrachttoffshop.enums.Brand;
import toff.novi.eindopdrachttoffshop.enums.Category;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByIsActiveTrue();

    List<Product> findByCategory(Category category);

    List<Product> findByBrand(Brand brand);

   List<Product> findByNameContainingIgnoreCase(String name);

   List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByBrandAndColorAndSize(Brand brand, Color color, Size size);
}
