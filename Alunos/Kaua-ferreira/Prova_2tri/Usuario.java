package com.monitoradeseries.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable { 
    private static final long serialVersionUID = 1L;

    private String nome;
    private List<Serie> seriesFavoritas;
    private List<Serie> seriesJaAssistidas;
    private List<Serie> seriesDesejoAssistir;

    public Usuario(String nome) {
        this.nome = nome;
        this.seriesFavoritas = new ArrayList<>();
        this.seriesJaAssistidas = new ArrayList<>();
        this.seriesDesejoAssistir = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Serie> getSeriesFavoritas() {
        return seriesFavoritas;
    }

    public List<Serie> getSeriesJaAssistidas() {
        return seriesJaAssistidas;
    }

    public List<Serie> getSeriesDesejoAssistir() {
        return seriesDesejoAssistir;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   
    public boolean adicionarSerieFavorita(Serie serie) {
        if (!seriesFavoritas.contains(serie)) {
            seriesFavoritas.add(serie);
            System.out.println("'" + serie.getName() + "' adicionada aos favoritos.");
            return true;
        } else {
            System.out.println("'" + serie.getName() + "' já está nos favoritos.");
            return false; 
        }
    }

    public boolean removerSerieFavorita(Serie serie) { 
        if (seriesFavoritas.remove(serie)) {
            System.out.println("'" + serie.getName() + "' removida dos favoritos.");
            return true;
        } else {
            System.out.println("'" + serie.getName() + "' não encontrada nos favoritos.");
            return false;
        }
    }

    public boolean adicionarSerieJaAssistida(Serie serie) { 
        if (!seriesJaAssistidas.contains(serie)) {
            seriesJaAssistidas.add(serie);
            System.out.println("'" + serie.getName() + "' adicionada às séries já assistidas.");
            return true; 
        } else {
            System.out.println("'" + serie.getName() + "' já está nas séries já assistidas.");
            return false; 
        }
    }

    public boolean removerSerieJaAssistida(Serie serie) { 
        if (seriesJaAssistidas.remove(serie)) {
            System.out.println("'" + serie.getName() + "' removida das séries já assistidas.");
            return true;
        } else {
            System.out.println("'" + serie.getName() + "' não encontrada nas séries já assistidas.");
            return false;
        }
    }

    public boolean adicionarSerieDesejoAssistir(Serie serie) {
        if (!seriesDesejoAssistir.contains(serie)) {
            seriesDesejoAssistir.add(serie);
            System.out.println("'" + serie.getName() + "' adicionada à lista 'Desejo Assistir'.");
            return true; 
        } else {
            System.out.println("'" + serie.getName() + "' já está na lista 'Desejo Assistir'.");
            return false; 
        }
    }

    public boolean removerSerieDesejoAssistir(Serie serie) { 
        if (seriesDesejoAssistir.remove(serie)) {
            System.out.println("'" + serie.getName() + "' removida da lista 'Desejo Assistir'.");
            return true;
        } else {
            System.out.println("'" + serie.getName() + "' não encontrada na lista 'Desejo Assistir'.");
            return false;
        }
    }
   

    public void imprimirSeries(List<Serie> lista, String titulo) {
        System.out.println("\n--- " + titulo + " ---");
        if (lista.isEmpty()) {
            System.out.println("Nenhuma série nesta lista.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getName() + " (Ano: " + lista.get(i).getPremiered() + ")");
        }
    }
}