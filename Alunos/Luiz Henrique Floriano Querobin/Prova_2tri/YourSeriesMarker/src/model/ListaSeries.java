package model;

import java.util.List;

public class ListaSeries {

    private TipoLista tipoLista;
    private List<Serie> serieList;

    public TipoLista getTipoLista() {
        return tipoLista;
    }

    public List<Serie> getSerieList() {
        return serieList;
    }

    public ListaSeries(TipoLista tipoLista, List<Serie> serieList) {
        this.tipoLista = tipoLista;
        this.serieList = serieList;
    }
}
