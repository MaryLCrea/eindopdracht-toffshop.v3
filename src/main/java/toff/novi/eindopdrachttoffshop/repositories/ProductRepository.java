package toff.novi.eindopdrachttoffshop.repositories;

import toff.novi.eindopdrachttoffshop.models.Product;
import toff.novi.eindopdrachttoffshop.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Zoek actieve producten
    List<Product> findByIsActiveTrue();

    // Zoek op naam (case insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Zoek op categorie (alleen actieve)
    List<Product> findByIsActiveTrueAndCategory(Category category);

    // Zoek op merk (alleen actieve)
    List<Product> findByIsActiveTrueAndBrand(Brand brand);

    // Zoek op maat (alleen actieve)
    List<Product> findByIsActiveTrueAndSize(Size size);

    // Zoek op kleur (alleen actieve)
    List<Product> findByIsActiveTrueAndColor(Color color);

    // Zoek op hak type (alleen actieve)
    List<Product> findByIsActiveTrueAndHeel(Heel heel);

    // Zoek binnen prijsrange
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Zoek producten op voorraad
    List<Product> findByIsActiveTrueAndStockQuantityGreaterThan(Integer minStock);

    // Zoek dansschoenen (Fashion categorie met merk)
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.category = ?1 AND p.brand IS NOT NULL")
    List<Product> findByIsActiveTrueAndCategoryAndBrandIsNotNull(Category category);

    // Zoek op meerdere criteria
    @Query("SELECT p FROM Product p WHERE p.isActive = true " +
            "AND (?1 IS NULL OR p.brand = ?1) " +
            "AND (?2 IS NULL OR p.color = ?2) " +
            "AND (?3 IS NULL OR p.size = ?3) " +
            "AND (?4 IS NULL OR p.heel = ?4)")
    List<Product> findByMultipleCriteria(Brand brand, Color color, Size size, Heel heel);

    // Zoek op naam en categorie
    List<Product> findByNameContainingIgnoreCaseAndCategory(String name, Category category);

    // Tel actieve producten per categorie
    @Query("SELECT COUNT(p) FROM Product p WHERE p.isActive = true AND p.category = ?1")
    Long countActiveProductsByCategory(Category category);

    // Zoek producten met lage voorraad
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.stockQuantity <= ?1")
    List<Product> findLowStockProducts(Integer threshold);
}
