package toff.novi.eindopdrachttoffshop.enums;

public enum OrderItemStatus {
    IN_CART("In winkelwagen"),
    ORDERED("Besteld"),
    SHIPPED("Verzonden"),
    DELIVERED("Afgeleverd"),
    CANCELLED("Geannuleerd");

    private final String displayName;

    OrderItemStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isInCart() {
        return this == IN_CART;
    }

    public boolean isOrdered() {
        return this == ORDERED;
    }

    public boolean isCompleted() {
        return this == DELIVERED;
    }

    public boolean isCancelled() {
        return this == CANCELLED;
    }

    @Override
    public String toString() {
        return displayName;
    }
}