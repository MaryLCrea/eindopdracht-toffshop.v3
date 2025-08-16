package toff.novi.eindopdrachttoffshop.enums;

public enum Category {
    FASHION("Fashion & Kleding"),
    HEALTH("Gezondheid & Wellness"),
    KITCHEN("Keuken & Apparatuur"),
    TOOLS("Gereedschap & Tuin");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
