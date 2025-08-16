package toff.novi.eindopdrachttoffshop.enums;

public enum Brand {
    PORTDANCE("PortDance"),
    RAYROSE("Ray Rose"),
    DIAMANT("Diamant"),
    BLOCH("Bloch"),
    CAPEZIO("Capezio"),
    DANSCO("Dansco"),
    SANSHA("Sansha"),
    WERNERKERN("Werner Kern"),
    DANCENATURALS("Dance Naturals"),
    NUEVAEPOCA("Nueva Epoca");

    private final String displayName;

    Brand(String displayName) {
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
