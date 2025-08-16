package toff.novi.eindopdrachttoffshop.enums;

public enum Heel {
    NONE("No Heel", 0.0),
    XLOW("Mini", 2.5),
    LOW("Laag", 3.5),
    MEDIUM("Middel", 5.0),
    HIGH("Hoog", 7.5),
    XLHIGH("Extra Hoog", 10.0),
    PLATFORM("Platform", 12.5);

    private final String displayName;
    private final Double heightCm;

    Heel(String displayName, Double heightCm) {
        this.displayName = displayName;
        this.heightCm = heightCm;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    @Override
    public String toString() {
        return displayName + " (" + heightCm + "cm)";
    }
}