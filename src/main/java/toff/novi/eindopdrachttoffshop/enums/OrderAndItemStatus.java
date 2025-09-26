package toff.novi.eindopdrachttoffshop.enums;

public enum OrderAndItemStatus {

    IN_CART(StatusType.CART, "In winkelwagen"),
    PENDING(StatusType.CART, "In behandeling"),
    CANCELLED_CART(StatusType.CART, "Geannuleerd (item)"),
    RETURNED_CART(StatusType.CART, "Geretourneerd (item)"),

    ORDERED(StatusType.ORDER, "Besteld"),
    SHIPPED(StatusType.ORDER, "Verzonden"),
    DELIVERED(StatusType.ORDER, "Afgeleverd"),
    CANCELLED_ORDER(StatusType.ORDER, "Geannuleerd (order)"),
    RETURNED_ORDER(StatusType.ORDER, "Geretourneerd (order)");

    private final StatusType type;
    private final String displayName;

    OrderAndItemStatus(StatusType type, String displayName) {
        this.type = type;
        this.displayName = displayName;
    }

    public StatusType getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isCartStatus() {
        return type == StatusType.CART;
    }

    public boolean isOrderStatus() {
        return type == StatusType.ORDER;
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
        return this == CANCELLED_CART || this == CANCELLED_ORDER;
    }

    public boolean isReturned() {
        return this == RETURNED_CART || this == RETURNED_ORDER;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public enum StatusType {
        CART,
        ORDER
    }
}