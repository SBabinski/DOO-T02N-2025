package model;

public enum TipoLista {
    FAVORITAS("Favoritas"),
    ASSISTIDAS("Assistidas"),
    PARA_ASSISTIR("Para assistir");

    private final String label;

    TipoLista(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
