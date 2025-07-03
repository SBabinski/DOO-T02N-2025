package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GerenciadorListaSeries {

    private List<Series> lista;
    private String nomeLista;

    public GerenciadorListaSeries(List<Series> listaExistente, String nomeLista) {
        this.lista = listaExistente;
        this.nomeLista = nomeLista;
    }

    public boolean adicionar(Series serie) {
        if (!lista.contains(serie)) {
            lista.add(serie);
            return true;
        }
        return false;
    }

    public boolean remover(int idSerie) {
        return lista.removeIf(serie -> serie.getId() == idSerie);
    }

    public List<Series> getListaOrdenada(int criterio) {
        List<Series> ordenada = new ArrayList<>(lista);

        switch (criterio) {
            case 1:
                ordenada.sort(Comparator.comparing(Series::getName));
                break;
            case 2:
                ordenada.sort(Comparator.comparing((Series s) -> {
                    Series.Rating rating = s.getRating();
                    return (rating != null && rating.getAverage() != null) ? rating.getAverage() : 0.0;
                }).reversed());
                break;
            case 3:
                ordenada.sort(Comparator.comparing(Series::getStatus, Comparator.nullsLast(String::compareTo)));
                break;
            case 4:
                ordenada.sort(Comparator.comparing(Series::getPremiered, Comparator.nullsLast(String::compareTo)));
                break;
            default:
                ordenada.sort(Comparator.comparing(Series::getName));
                break;
        }
        return ordenada;
    }

    public List<Series> getLista() {
        return lista;
    }

    public String getNomeLista() {
        return nomeLista;
    }
}