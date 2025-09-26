package toff.novi.eindopdrachttoffshop.exceptions;

public class ProductValidationException extends RuntimeException {

    public ProductValidationException(String message) {
        super(message);
    }

    public static ProductValidationException notFound(String sku) {
        return new ProductValidationException("Product met SKU " + sku + " bestaat niet in de database.");
    }

    public static ProductValidationException priceMismatch(String sku) {
        return new ProductValidationException("Prijs komt niet overeen voor product met SKU " + sku);
    }

    public static ProductValidationException nameMismatch(String sku) {
        return new ProductValidationException("Productnaam komt niet overeen met de database voor SKU " + sku);
    }
}

