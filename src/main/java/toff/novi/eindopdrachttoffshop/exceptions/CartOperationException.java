package toff.novi.eindopdrachttoffshop.exceptions;

public class CartOperationException extends RuntimeException {

    public CartOperationException(String message) {
        super(message);
    }

    public CartOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

