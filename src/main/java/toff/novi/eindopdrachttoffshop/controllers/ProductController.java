package toff.novi.eindopdrachttoffshop.controllers;

import toff.novi.eindopdrachttoffshop.dtos.ProductRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.ProductResponseDto;
import toff.novi.eindopdrachttoffshop.enums.*;
import toff.novi.eindopdrachttoffshop.enums.Brand;
import toff.novi.eindopdrachttoffshop.enums.Color;
import toff.novi.eindopdrachttoffshop.enums.Size;
import toff.novi.eindopdrachttoffshop.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toff.novi.eindopdrachttoffshop.services.FileStorageService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;
    private final FileStorageService fileStorageService;

    public ProductController(ProductService productService, FileStorageService fileStorageService) {
        this.productService = productService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("total", products.size());
        response.put("message", "Alle producten opgehaald");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveProducts() {
        List<ProductResponseDto> products = productService.getActiveProducts();

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("total", products.size());
        response.put("message", "Actieve producten opgehaald");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Integer id) {
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(@RequestParam String name) {
        List<ProductResponseDto> products = productService.searchProductsByName(name);

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("total", products.size());
        response.put("searchTerm", name);
        response.put("message", "Zoekresultaten voor: " + name);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getProductsByCategory(@PathVariable Category category) {
        List<ProductResponseDto> products = productService.getProductsByCategory(category);

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("total", products.size());
        response.put("category", category);
        response.put("message", "Producten in categorie: " + category.getDisplayName());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<Map<String, Object>> getProductsByBrand(@PathVariable Brand brand) {
        List<ProductResponseDto> products = productService.getProductsByBrand(brand);

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("total", products.size());
        response.put("brand", brand);
        response.put("message", "Producten van merk: " + brand.getDisplayName());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/price-range")
    public ResponseEntity<Map<String, Object>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {

        List<ProductResponseDto> products = productService.getProductsByPriceRange(minPrice, maxPrice);

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("total", products.size());
        response.put("minPrice", minPrice);
        response.put("maxPrice", maxPrice);
        response.put("message", "Producten tussen €" + minPrice + " en €" + maxPrice);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dance-shoes")
    public ResponseEntity<Map<String, Object>> getDanceShoes(
            @RequestParam(required = false) Brand brand,
            @RequestParam(required = false) Color color,
            @RequestParam(required = false) Size size) {

        List<ProductResponseDto> products;

        if (brand != null && color != null && size != null) {
            products = productService.getDanceShoesByBrandColorAndSize(brand, color, size);
        } else {

            products = productService.getProductsByCategory(Category.FASHION);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("total", products.size());
        response.put("filters", Map.of(
                "brand", brand != null ? brand : "alle",
                "color", color != null ? color : "alle",
                "size", size != null ? size : "alle"
        ));
        response.put("message", "Dansschoenen opgehaald");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getCategories() {
        Category[] categories = Category.values();

        Map<String, Object> response = new HashMap<>();
        response.put("categories", categories);
        response.put("total", categories.length);
        response.put("message", "Alle categorieën opgehaald");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/brands")
    public ResponseEntity<Map<String, Object>> getBrands() {
        Brand[] brands = Brand.values();

        Map<String, Object> response = new HashMap<>();
        response.put("brands", brands);
        response.put("total", brands.length);
        response.put("message", "Alle merken opgehaald");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sizes")
    public ResponseEntity<Map<String, Object>> getSizes() {
        Size[] sizes = Size.values();

        Map<String, Object> response = new HashMap<>();
        response.put("sizes", sizes);
        response.put("total", sizes.length);
        response.put("message", "Alle maten opgehaald");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/colors")
    public ResponseEntity<Map<String, Object>> getColors() {
        Color[] colors = Color.values();

        Map<String, Object> response = new HashMap<>();
        response.put("colors", colors);
        response.put("total", colors.length);
        response.put("message", "Alle kleuren opgehaald");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@Valid @RequestBody ProductRequestDto productDto) {
        ProductResponseDto product = productService.createProduct(productDto);

        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("message", "Product succesvol aangemaakt");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<Map<String, Object>> uploadProductImage(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file) {

        try {
            ProductResponseDto product = productService.getProductById(id);

            if (file.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Geen bestand geselecteerd");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Alleen afbeeldingen zijn toegestaan");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            String fileName = fileStorageService.storeFile(file);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/download/")
                    .path(fileName)
                    .toUriString();

            ProductResponseDto updatedProduct = productService.updateProductImage(id, fileName, fileDownloadUri);

            Map<String, Object> response = new HashMap<>();
            response.put("product", updatedProduct);
            response.put("fileName", fileName);
            response.put("fileDownloadUri", fileDownloadUri);
            response.put("message", "Afbeelding succesvol geüpload");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Upload gefaald: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}/delete-image")
    public ResponseEntity<Map<String, Object>> deleteProductImage(@PathVariable Integer id) {
        try {
            ProductResponseDto product = productService.getProductById(id);

            if (product.getImageName() != null) {

                fileStorageService.deleteFile(product.getImageName());


                ProductResponseDto updatedProduct = productService.updateProductImage(id, null, null);

                Map<String, Object> response = new HashMap<>();
                response.put("product", updatedProduct);
                response.put("message", "Afbeelding succesvol verwijderd");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Product heeft geen afbeelding");
                return ResponseEntity.ok(response);
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Verwijderen gefaald: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequestDto productDto) {

        ProductResponseDto product = productService.updateProduct(id, productDto);

        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("message", "Product succesvol bijgewerkt");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Map<String, Object>> updateStock(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> request) {

        Integer newStock = request.get("stockQuantity");
        if (newStock == null || newStock < 0) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Ongeldige voorraad waarde");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        ProductResponseDto product = productService.updateStock(id, newStock);

        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("message", "Voorraad bijgewerkt naar: " + newStock);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateProduct(@PathVariable Integer id) {
        ProductResponseDto product = productService.deactivateProduct(id);

        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("message", "Product gedeactiveerd");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product succesvol verwijderd");

        return ResponseEntity.ok(response);
    }
}

