package toff.novi.eindopdrachttoffshop.enums;

public enum Heel {
    FLAT("No Heel", 0.0),
    LOW("Mini", 2.5),
    MEDIUM("Middel", 5.0),
    HIGH("Hoog", 7.5),
    PLATFORM("Platform", 10.0);

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