package toff.novi.eindopdrachttoffshop.enums;

public enum Color {
    BLACK("Zwart"),
    WHITE("Wit"),
    NUDE("Nude"),
    PINK("Roze"),
    RED("Rood"),
    GLITTER("Glitter"),
    DARKTAN("Dark Tan"),
    SILVER("Zilver"),
    GOLD("Goud"),
    BURGUNDY("Bordeaux");


    private final String displayName;

    Color(String displayName) {
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
