package toff.novi.eindopdrachttoffshop.repositories;

import toff.novi.eindopdrachttoffshop.enums.Brand;
import toff.novi.eindopdrachttoffshop.enums.Color;
import toff.novi.eindopdrachttoffshop.enums.Heel;
import toff.novi.eindopdrachttoffshop.enums.Size;
import toff.novi.eindopdrachttoffshop.models.Product;
import toff.novi.eindopdrachttoffshop.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    List<Product> findByIsActiveTrue();


    List<Product> findByProductNameContainingIgnoreCase(String productName);


    List<Product> findByIsActiveTrueAndCategory(Category category);

    List<Product> findByIsActiveTrueAndBrand(Brand brand);


    List<Product> findByIsActiveTrueAndSize(Size size);


    List<Product> findByIsActiveTrueAndColor(Color color);


    List<Product> findByIsActiveTrueAndHeel(Heel heel);


    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);


    List<Product> findByIsActiveTrueAndStockQuantityGreaterThan(Integer minStock);

    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.category = ?1 AND p.brand IS NOT NULL")
    List<Product> findByIsActiveTrueAndCategoryAndBrandIsNotNull(Category category);

    @Query("SELECT p FROM Product p WHERE p.isActive = true " +
            "AND (?1 IS NULL OR p.brand = ?1) " +
            "AND (?2 IS NULL OR p.color = ?2) " +
            "AND (?3 IS NULL OR p.size = ?3) " +
            "AND (?4 IS NULL OR p.heel = ?4)")

    List<Product> findByMultipleCriteria(Brand brand, Color color, Size size, Heel heel);


    List<Product> findByProductNameContainingIgnoreCaseAndCategory(String name, Category category);


    @Query("SELECT COUNT(p) FROM Product p WHERE p.isActive = true AND p.category = ?1")
    Long countActiveProductsByCategory(Category category);


    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.stockQuantity <= ?1")
    List<Product> findLowStockProducts(Integer threshold);

    Optional<Product> findBySku(String sku);

}
