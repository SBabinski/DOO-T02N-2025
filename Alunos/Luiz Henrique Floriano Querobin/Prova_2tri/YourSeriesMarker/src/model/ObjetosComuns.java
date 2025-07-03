package model;

import model.helper.JsonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjetosComuns {
    private Usuario usuario;
    private final ListaSeries listaSeriesFavoritas;
    private final ListaSeries listaSeriesAssistidas;
    private final ListaSeries listaSeriesParaAssistir;

    // Funções de lista

    public void adicionaSerieNaLista(Serie serie, TipoLista tipoLista) {
        serie.getListas().add(tipoLista);
        ListaSeries listaSeries = this.getListaSeries(tipoLista);
        listaSeries.getSerieList().add(serie);
        JsonHelper.gravarJsonSerie(serie);
    }

    public void adicionaSerieNaListaSemGravar(Serie serie, TipoLista tipoLista) {
        ListaSeries listaSeries = this.getListaSeries(tipoLista);
        listaSeries.getSerieList().add(serie);
    }

    public void removeSerieDaLista(Serie serie, TipoLista tipoLista) {
        serie.getListas().removeIf(tipoListaSerie -> tipoListaSerie == tipoLista);
        ListaSeries listaSeries = getListaSeries(tipoLista);
        listaSeries.getSerieList().remove(serie);
        JsonHelper.gravarJsonSerie(serie);
    }

    public ListaSeries getListaSeries(TipoLista tipoLista) {
        switch (tipoLista) {
            case FAVORITAS -> {
                return listaSeriesFavoritas;
            }
            case ASSISTIDAS -> {
                return listaSeriesAssistidas;
            }
            case PARA_ASSISTIR -> {
                return listaSeriesParaAssistir;
            }
        }
        return null;
    }

    // Funções gerais

    public ObjetosComuns() {
        this.usuario = JsonHelper.buscaUsuarioGravado();
        this.listaSeriesFavoritas = new ListaSeries(TipoLista.FAVORITAS, new ArrayList<Serie>());
        this.listaSeriesAssistidas = new ListaSeries(TipoLista.ASSISTIDAS, new ArrayList<Serie>()); // Corrigi o tipo aqui
        this.listaSeriesParaAssistir = new ListaSeries(TipoLista.PARA_ASSISTIR, new ArrayList<Serie>());

        List<Serie> series = new ArrayList<>(JsonHelper.buscaSeriesGravadas());

        for (Serie serie : series) {
            if (serie != null && serie.getListas() != null) {
                for (TipoLista tipoLista : new ArrayList<>(serie.getListas())) {
                    this.adicionaSerieNaListaSemGravar(serie, tipoLista);
                }
            }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ListaSeries getListaSeriesAssistidas() {
        return listaSeriesAssistidas;
    }

    public ListaSeries getListaSeriesFavoritas() {
        return listaSeriesFavoritas;
    }

    public ListaSeries getListaSeriesParaAssistir() {
        return listaSeriesParaAssistir;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
