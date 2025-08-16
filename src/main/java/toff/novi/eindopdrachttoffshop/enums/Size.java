package toff.novi.eindopdrachttoffshop.enums;

public enum Size {
    SIZE_35("35", 35),
    SIZE_36("36", 36),
    SIZE_37("37", 37),
    SIZE_38("38", 38),
    SIZE_39("39", 39),
    SIZE_40("40", 40),
    SIZE_41("41", 41),
    SIZE_42("42", 42),
    SIZE_43("43", 43),
    SIZE_44("44", 44),
    SIZE_45("45", 45);

    private final String displayName;
    private final Integer numericSize;

    Size(String displayName, Integer numericSize) {
        this.displayName = displayName;
        this.numericSize = numericSize;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getNumericSize() {
        return numericSize;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
