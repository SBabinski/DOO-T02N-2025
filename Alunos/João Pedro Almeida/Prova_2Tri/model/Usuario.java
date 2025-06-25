package com.cinelume.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private final String nome;
    private final Map<String, List<Serie>> listas;

    public Usuario(String nome) {
        this.nome = nome;
        this.listas = new HashMap<>();
        this.listas.put("favorites", new ArrayList<>());
        this.listas.put("watched", new ArrayList<>());
        this.listas.put("watchlist", new ArrayList<>());
    }

    public void adicionarSerie(String tipoLista, Serie serie) {
        if (!listas.get(tipoLista).contains(serie)) {
            listas.get(tipoLista).add(serie);
        }
    }

    public boolean removerSerie(String tipoLista, Serie serie) {
        return listas.get(tipoLista).remove(serie);
    }

    public void ordenarLista(String tipoLista, String criterio) {
        Comparator<Serie> comparator = switch (criterio.toLowerCase()) {
            case "nome" -> Comparator.comparing(Serie::getNome);
            case "nota" -> Comparator.comparingDouble(Serie::getNota).reversed();
            case "status" -> Comparator.comparing(Serie::getStatus);
            case "data" -> Comparator.comparing(Serie::getDataEstreia);
            default -> null;
        };
        
        if (comparator != null) {
            listas.get(tipoLista).sort(comparator);
        }
    }

    public List<Serie> getLista(String tipoLista) {
        return new ArrayList<>(listas.get(tipoLista));
    }

    public String getNome() {
        return nome;
    }
}